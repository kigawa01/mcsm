package net.kigawa.mcsm

import net.kigawa.mcsm.util.PlatFormInstance
import kotlin.system.exitProcess

fun main(args: Array<String>) {
  val platFormInstance = PlatFormInstance(
    JvmLogger()
  ) { exitProcess(it) }
  Main(platFormInstance).main(args)
}
