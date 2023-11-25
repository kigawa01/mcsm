package net.kigawa.mcsm.util.net

import kotlinx.coroutines.channels.Channel

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ServerSocketConnection {
  actual fun reader(): Channel<String> {
    TODO("Not yet implemented")
  }

  actual fun writer(): Channel<String> {
    TODO("Not yet implemented")
  }
}