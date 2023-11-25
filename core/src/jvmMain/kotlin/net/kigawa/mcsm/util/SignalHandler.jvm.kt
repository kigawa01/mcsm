package net.kigawa.mcsm.util

import kotlin.concurrent.thread

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object SignalHandler {
  actual fun shutdownHook(hook: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(thread(start = false) {
      hook()
    })
  }
}