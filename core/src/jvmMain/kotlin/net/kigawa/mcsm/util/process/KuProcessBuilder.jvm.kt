package net.kigawa.mcsm.util.process

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuProcessBuilder actual constructor(args: Array<String>) {
  private val processBuilder = ProcessBuilder(*args)
  actual fun start(): KuProcess {
    return JvmProcess(processBuilder.start())
  }
}