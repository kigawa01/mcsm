package net.kigawa.mcsm.util.io

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*

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

  actual fun toAbsolute(): KuPath {
    return KuPath(path.toAbsolutePath())
  }

  actual fun strPath(): String {
    return path.pathString
  }

  fun javaPath() = path
  actual fun parent(): KuPath {
    return KuPath(path.parent)
  }

  actual fun createDirOrGet(): KuDirectory {
    if (!path.isDirectory()) Files.createDirectory(path)
    return KuDirectory(path.toFile())
  }

  actual fun toFile(): KuFile {
    return KuFile(path.toFile())
  }

  actual fun isExists(): Boolean {
    return path.exists()
  }

  actual fun removeIfExists() {
    path.deleteIfExists()
  }
}