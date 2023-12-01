package net.kigawa.mcsm.util.logger

import net.kigawa.kutil.log.log.fomatter.KFileFormatter
import java.util.logging.ConsoleHandler
import java.util.logging.Handler

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ConsoleLoggerHandler : LoggerHandler {
  private val nativeConsoleHandler = ConsoleHandler()

  init {
    nativeConsoleHandler.formatter = KFileFormatter()
  }

  override fun nativeHandler(): Handler {
    return nativeConsoleHandler
  }

  override fun setLevel(level: LogLevel) {
    nativeConsoleHandler.level = level.javaLevel()
  }
}