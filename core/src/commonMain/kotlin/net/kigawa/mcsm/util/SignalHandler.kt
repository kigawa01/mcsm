package net.kigawa.mcsm.util

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SignalHandler {
  fun shutdownHook(hook: () -> Unit)
}