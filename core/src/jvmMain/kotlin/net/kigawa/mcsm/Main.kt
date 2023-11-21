package net.kigawa.mcsm

import net.kigawa.mcsm.util.platform.PlatFormInstance

fun main(args: Array<String>) {
  val platFormInstance = PlatFormInstance(
    JvmLogger()
  )
  { System.getenv(it) }
  Main(platFormInstance).main(args)
}