package net.kigawa.mcsm.util

import net.kigawa.mcsm.util.io.KuPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual enum class Paths private constructor(path: KuPath) {
  JAVA(KuPath(""))
  ;

  actual val path: KuPath
    get() = TODO("Not yet implemented")

}