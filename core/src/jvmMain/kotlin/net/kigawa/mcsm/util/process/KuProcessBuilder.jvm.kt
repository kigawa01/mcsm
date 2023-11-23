package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.io.JvmIoException
import net.kigawa.mcsm.util.tryCatch
import java.io.IOException

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuProcessBuilder actual constructor(vararg args: String) {
  private val processBuilder = ProcessBuilder(*args)

  actual fun start(): KuProcess {
    tryCatch({ return JvmProcess(processBuilder.start()) }, { _, e: IOException -> throw JvmIoException(e) })
  }
}