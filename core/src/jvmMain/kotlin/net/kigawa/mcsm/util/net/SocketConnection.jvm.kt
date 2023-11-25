package net.kigawa.mcsm.util.net

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.SuspendCloseable
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.nio.charset.StandardCharsets

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ServerSocketConnection(
  private val socketChannel: SocketChannel,
) : SuspendCloseable {
  private val reader = Channel<String>()
  private val writer = Channel<String>()
  private val readTask = Coroutines.launchIo {
    while (socketChannel.isConnected && isActive) {
      val buf = ByteBuffer.allocate(1024)
      val read = Coroutines.withContextIo {
        socketChannel.read(buf)
      }
      if (read > 0) {
        val sb = StringBuilder()
        buf.flip()
        val str = String(buf.array(), StandardCharsets.UTF_8)

        str.forEach {
          if (it == '\n') {
            reader.send(it.toString())
            sb.clear()
            return@forEach
          }
          sb.append(it)
        }
      }
    }
  }
  private val writerTask = Coroutines.launchIo {
    for (str in writer) {
      socketChannel.write(ByteBuffer.wrap(str.toByteArray(StandardCharsets.UTF_8)))
    }
  }

  actual fun reader(): Channel<String> {
    return reader
  }

  actual fun writer(): Channel<String> {
    return writer
  }

  override suspend fun suspendClose() {
    readTask.cancelAndJoin()
    reader.close()
    writer.close()
    writerTask.cancelAndJoin()
  }
}