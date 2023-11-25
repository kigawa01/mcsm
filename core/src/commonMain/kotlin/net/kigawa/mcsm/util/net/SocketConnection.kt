package net.kigawa.mcsm.util.net

import kotlinx.coroutines.channels.Channel
import net.kigawa.mcsm.util.io.SuspendCloseable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SocketConnection : SuspendCloseable {
  fun reader(): Channel<String>
  fun writer(): Channel<String>
}