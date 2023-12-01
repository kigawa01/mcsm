package net.kigawa.mcsm.util.logger

import java.util.logging.Handler

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual interface LoggerHandler {
  fun nativeHandler(): Handler
  fun setLevel(level: LogLevel)
}