package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.io.KuDirectory

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KuProcessBuilder(vararg args: String) {
  fun start(): KuProcess
  fun workDir(directory: KuDirectory?): KuProcessBuilder
}