package net.kigawa.mcsm.util.net

import kotlinx.coroutines.channels.Channel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ServerSocketConnection {
  fun reader(): Channel<String>
  fun writer(): Channel<String>
}