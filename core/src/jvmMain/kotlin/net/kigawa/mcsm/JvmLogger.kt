package net.kigawa.mcsm

import net.kigawa.kutil.log.log.KLogger
import net.kigawa.kutil.log.log.fomatter.KFileFormatter
import net.kigawa.mcsm.util.logger.KuLogger
import java.util.logging.ConsoleHandler
import java.util.logging.Level


class JvmLogger : KuLogger {
  private val nativeLogger = KLogger(javaClass.name, null, Level.INFO, null)

  init {
    nativeLogger.enable()
    nativeLogger.parent.handlers.filterIsInstance<ConsoleHandler>().forEach {
      it.formatter = KFileFormatter()
    }
  }

  override fun info(message: String) {
    nativeLogger.info(message)
  }

  override fun warning(message: String) {
    nativeLogger.log(Level.WARNING, message)
  }
}