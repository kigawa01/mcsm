package net.kigawa.mcsm.util.net

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.SuspendCloseable
import net.kigawa.mcsm.util.logger.KuLogger
import java.nio.ByteBuffer
import java.nio.channels.ClosedChannelException
import java.nio.channels.SocketChannel
import java.nio.charset.StandardCharsets

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketConnection(
  private val socketChannel: SocketChannel,
  private val logger: KuLogger?,
  private val coroutines: Coroutines,
) : SuspendCloseable {
  private val reader = Channel<String>()
  private val writer = Channel<String>()
  private val readTask = coroutines.launchIo {
    try {
      while (socketChannel.isConnected && isActive) {
        logger?.fine("reading socket..")
        @Suppress("CanBeVal")
        var buf = ByteBuffer.allocate(1024)
        val read = coroutines.withContextIo {
          socketChannel.read(buf)
        }
        logger?.fine("read socket $buf")
        if (read <= 0) {
          suspendClose()
          break
        }

        val sb = StringBuilder()
        buf.flip()
        val str = StandardCharsets.UTF_8.decode(buf).toString()

        logger?.fine("read text '$str'")

        str.forEach {
          if (it == '\n') {
            reader.send(sb.toString())
            sb.clear()
            return@forEach
          }
          sb.append(it)
        }
      }
    } catch (_: ClosedChannelException) {
    } finally {
      reader.close()
    }
  }
  private val writerTask = coroutines.launchIo {
    socketChannel.use { socketChannel ->
      for (str in writer) {
        @Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
        var buf = ByteBuffer.allocate(1024)
        buf = StandardCharsets.UTF_8.encode((str + "\n"))
        logger?.fine("write text to socket $str")
        socketChannel.write(buf)
      }
    }
  }

  actual fun reader(): Channel<String> {
    return reader
  }

  actual fun writer(): Channel<String> {
    return writer
  }

  override suspend fun suspendClose() {
    writer.close()
    delay(100)
    writerTask.cancelAndJoin()
    readTask.cancelAndJoin()
  }
}