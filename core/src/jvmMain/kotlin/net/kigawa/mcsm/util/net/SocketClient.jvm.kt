package net.kigawa.mcsm.util.net

import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable
import java.net.UnixDomainSocketAddress
import java.nio.channels.SocketChannel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketClient actual constructor(
  path: KuPath,
) : SuspendCloseable {
  private val socketAddress = UnixDomainSocketAddress.of(path.javaPath())
  private val socketChannel = SocketChannel.open(socketAddress)
  actual fun connect(): SocketConnection {
//    socketChannel.connect(socketAddress)
    return SocketConnection(socketChannel)
  }

  override suspend fun suspendClose() {
    Coroutines.withContextIo {
      close()
    }
  }

  override fun close() {
    socketChannel.close()
  }
}