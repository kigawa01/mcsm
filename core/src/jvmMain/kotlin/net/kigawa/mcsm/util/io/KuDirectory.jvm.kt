package net.kigawa.mcsm.util.io

import java.io.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuDirectory(
  nativeDirectory: File
) {
  init {
    if (!nativeDirectory.isDirectory)
      throw NotDirectoryException("$nativeDirectory is not directory")
  }
}