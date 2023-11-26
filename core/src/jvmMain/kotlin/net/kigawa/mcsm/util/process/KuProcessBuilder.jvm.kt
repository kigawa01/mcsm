package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.io.JvmIoException
import java.io.IOException

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuProcessBuilder actual constructor(vararg args: String) {
  private val processBuilder = ProcessBuilder(*args)

  actual fun start(): KuProcess {
    try {
      return JvmProcess(processBuilder.start())
    } catch (e: IOException) {
      throw JvmIoException(e)
    }
  }
}