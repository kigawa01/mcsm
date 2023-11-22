package net.kigawa.mcsm

import net.kigawa.mcsm.util.PlatFormInstance
import net.kigawa.mcsm.util.os.OSType
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
  val platFormInstance = PlatFormInstance(
    JvmLogger(),
    { System.getenv(it) },
    getOsType(),
    { exitProcess(it) }
  )
  Main(platFormInstance).main(args)
}

val IS_WINDOWS = System.getProperty("os.name").lowercase(Locale.getDefault()).startsWith("win")
val IS_MAC = System.getProperty("os.name").lowercase(Locale.getDefault()).startsWith("mac")
val IS_LINUX = System.getProperty("os.name").lowercase(Locale.getDefault()).startsWith("linux")
fun getOsType(): OSType {
  if (IS_WINDOWS) return OSType.WINDOWS
  if (IS_MAC) return OSType.MAC
  return OSType.OTHER
}