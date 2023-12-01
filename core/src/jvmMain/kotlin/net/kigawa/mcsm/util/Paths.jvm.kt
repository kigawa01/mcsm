package net.kigawa.mcsm.util

import net.kigawa.mcsm.util.io.KuPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual enum class Paths(
  actual val path: KuPath,
) {
  JAVA(KuPath(System.getProperty("java.home")).join("bin/java"))
  ;

}