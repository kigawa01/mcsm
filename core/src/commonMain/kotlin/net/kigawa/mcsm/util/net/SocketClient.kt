package net.kigawa.mcsm.util.net

import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SocketClient(
  path: KuPath,
) : SuspendCloseable {
  fun connect(): SocketConnection
}