package net.kigawa.mcsm.util.logger

import kotlin.reflect.KClass

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuLogger actual constructor(context: KClass<*>, handlers: List<LoggerHandler>) {
  actual fun info(message: String) {
  }

  actual fun fine(message: String) {
  }

  actual fun warning(message: String) {
  }
}