package net.kigawa.mcsm.util.process

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KuProcessBuilder(args: Array<String>) {
  fun start(): KuProcess
}