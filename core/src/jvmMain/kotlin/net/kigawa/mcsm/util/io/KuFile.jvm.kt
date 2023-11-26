package net.kigawa.mcsm.util.io

import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuFile(
  private val nativeFile: File
) {
  actual fun createFile() {
    nativeFile.createNewFile()
  }
}