package net.kigawa.mcsm

import net.kigawa.mcsm.util.PlatFormInstance
import kotlin.system.exitProcess

fun main(args: Array<String>) {
  val platFormInstance = PlatFormInstance(
    JvmLogger(),
    { System.getenv(it) },
    { exitProcess(it) }
  )
  Main(platFormInstance).main(args)
}
