package net.kigawa.mcsm.util.net

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable
import java.net.StandardProtocolFamily
import java.net.UnixDomainSocketAddress
import java.nio.channels.ServerSocketChannel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketServer actual constructor(
  path: KuPath,
) : SuspendCloseable {
  private val socketAddress = UnixDomainSocketAddress.of(path.javaPath())
  private val serverSocketChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX).bind(socketAddress)
  private val conChannel = Channel<SocketConnection>()
  private val bindTask = Coroutines.launchIo(start = CoroutineStart.LAZY) {
    try {
      while (serverSocketChannel.isOpen && isActive) {
        conChannel.send(SocketConnection(serverSocketChannel.accept()))
      }

    } finally {
      conChannel.close()
      serverSocketChannel.close()
    }
  }

  actual fun bind(): Channel<SocketConnection> {
    bindTask.start()
    return conChannel
  }

  override suspend fun suspendClose() {
    bindTask.cancel()

    Coroutines.withContextIo {
      serverSocketChannel.close()
    }

    bindTask.join()
  }
}