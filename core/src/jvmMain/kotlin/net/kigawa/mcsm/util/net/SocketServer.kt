package net.kigawa.mcsm.util.net

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable
import net.kigawa.mcsm.util.logger.KuLogger
import java.net.BindException
import java.net.StandardProtocolFamily
import java.net.UnixDomainSocketAddress
import java.nio.channels.AsynchronousCloseException
import java.nio.channels.ServerSocketChannel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketServer actual constructor(
  path: KuPath,
  private val logger: KuLogger?,
  private val coroutines: Coroutines,
) : SuspendCloseable {
  private val socketAddress = UnixDomainSocketAddress.of(path.javaPath())
  private val conChannel = Channel<SocketConnection>()
  private val serverSocketChannel: ServerSocketChannel
  private val bindTask: Job

  init {
    if (path.isExists()) path.remove()
    try {
      serverSocketChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX).bind(socketAddress)
    } catch (e: BindException) {
      conChannel.close()
      throw e
    }
    bindTask = coroutines.launchIo(start = CoroutineStart.LAZY) {
      try {
        while (serverSocketChannel.isOpen && isActive) {
          conChannel.send(SocketConnection(serverSocketChannel.accept(), logger, coroutines))
          logger?.fine("client connected")
        }

      } catch (_: AsynchronousCloseException) {
      } finally {
        conChannel.close()
        serverSocketChannel.close()
      }
    }
  }

  actual fun bind(): Channel<SocketConnection> {
    bindTask.start()
    return conChannel
  }

  override suspend fun suspendClose() {
    coroutines.withContextIo {
      serverSocketChannel.close()
    }

    bindTask.join()
  }
}