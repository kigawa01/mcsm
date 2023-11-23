package net.kigawa.mcsm.util.io

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KuPath(
  strPath: String,
) {
  companion object {
    val separator: String
  }

  fun join(child: String): KuPath
  fun join(child: KuPath): KuPath
  fun strPath(): String
}