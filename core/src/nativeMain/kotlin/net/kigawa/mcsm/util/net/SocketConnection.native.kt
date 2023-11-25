package net.kigawa.mcsm.util.net

import kotlinx.coroutines.channels.Channel
import net.kigawa.mcsm.util.io.SuspendCloseable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class SocketConnection : SuspendCloseable {
  actual fun reader(): Channel<String> {
    TODO("Not yet implemented")
  }

  actual fun writer(): Channel<String> {
    TODO("Not yet implemented")
  }

  override suspend fun suspendClose() {
    TODO("Not yet implemented")
  }
}