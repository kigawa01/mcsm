package net.kigawa.mcsm.util.io

import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KuPath(
  private val path: Path,
) {
  actual companion object {
    actual val separator: String
      get() = File.separator
  }

  actual constructor(strPath: String) : this(Path(strPath))

  actual fun join(child: String): KuPath {
    return KuPath(path.resolve(child))
  }

  actual fun join(child: KuPath): KuPath {
    return KuPath(path.resolve(child.path))
  }

  actual fun strPath(): String {
    return path.pathString
  }

  fun javaPath() = path
}