package net.kigawa.mcsm.util.net

import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketClient actual constructor(path: KuPath) : SuspendCloseable {
  override suspend fun suspendClose() {
    TODO("Not yet implemented")
  }

  actual fun connect(): SocketConnection {
    TODO("Not yet implemented")
  }
}