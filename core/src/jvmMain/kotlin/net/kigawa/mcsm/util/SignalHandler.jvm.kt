package net.kigawa.mcsm.util

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object SignalHandler {
  actual fun shutdownHook(hook: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(Thread { hook() })
  }
}