package net.kigawa.mcsm.util.os

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import net.kigawa.mcsm.util.KutilPlatform
import net.kigawa.mcsm.util.Version
import net.kigawa.mcsm.util.io.DefaultIo
import net.kigawa.mcsm.util.process.KuProcessBuilder
import net.kigawa.mcsm.util.tryCatch

object KutilOS {
  private val OS_NAME = KutilPlatform.getSystemProperty("os.name")
  val OS_TYPE = getOsType()
  val OS_VERSION = getVersion()

  private fun getOsType(): OSType {
    if (OS_NAME.lowercase().startsWith("win")) return OSType.WINDOWS
    if (OS_NAME.lowercase().startsWith("mac")) return OSType.MAC
    if (OS_NAME.lowercase().startsWith("linux")) {
      return getLinuxType()
    }
    return OSType.OTHER
  }

  private fun getVersion(): Version? {
    return when (OS_TYPE) {
      OSType.LINUX -> {
        val process = KuProcessBuilder("lsb_release", "-i").start()
        CoroutineScope(Dispatchers.IO).launch {
          process.errReader().tryReadContinue {
            DefaultIo.error.writeLine(it)
          }
        }
        val strVersion = process.reader().tryCatch {
          it.readLine()
            ?.split(":")
            ?.getOrNull(1)
            ?.trim()
        } ?: return null

        return Version(strVersion)
      }

      else -> null
    }
  }

  private fun getLinuxType(): OSType {
    val process = KuProcessBuilder("lsb_release", "-i").start()
    CoroutineScope(Dispatchers.IO).launch {
      process.errReader().tryReadContinue {
        DefaultIo.error.writeLine(it)
      }
    }
    val id = process.reader().tryCatch {
      it.readLine()
        ?.split(":")
        ?.getOrNull(1)
        ?.trim()
    } ?: return OSType.LINUX

    if (id.lowercase() == "ubuntu") return OSType.UBUNTU
    return OSType.LINUX
  }
}