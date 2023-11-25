package net.kigawa.mcsm.util.net

import kotlinx.coroutines.channels.Channel
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketServer actual constructor(path: KuPath) : SuspendCloseable {
  actual fun bind(): Channel<ServerSocketConnection> {
    TODO("Not yet implemented")
  }

  override suspend fun suspendClose() {
    TODO("Not yet implemented")
  }
}