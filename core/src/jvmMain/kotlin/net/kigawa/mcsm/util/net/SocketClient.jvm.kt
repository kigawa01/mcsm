package net.kigawa.mcsm.util.net

import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable
import net.kigawa.mcsm.util.logger.KuLogger
import java.net.UnixDomainSocketAddress
import java.nio.channels.SocketChannel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketClient actual constructor(
  path: KuPath,
  private val logger: KuLogger,
  private val coroutines: Coroutines,
) : SuspendCloseable {
  private val socketAddress = UnixDomainSocketAddress.of(path.javaPath())
  private val socketChannel = SocketChannel.open(socketAddress)
  actual fun connect(): SocketConnection {
    return SocketConnection(socketChannel, logger, coroutines)
  }

  override suspend fun suspendClose() {
    coroutines.withContextIo {
      close()
    }
  }

  override fun close() {
    socketChannel.close()
  }
}