package net.kigawa.mcsm.util.io

import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuFile(
  val nativeFile: File,
) {
  actual fun remove() {
    nativeFile.delete()
  }
}