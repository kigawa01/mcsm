package net.kigawa.mcsm.util.logger

import kotlin.reflect.KClass

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KuLogger(
  context: KClass<*>,
  handlers: List<LoggerHandler> = listOf(),
  level: LogLevel = LogLevel.INFO,
) {
  val level: LogLevel
  fun info(message: String)
  fun fine(message: String)
  fun warning(message: String)
}