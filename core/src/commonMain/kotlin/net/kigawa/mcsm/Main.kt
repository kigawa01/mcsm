package net.kigawa.mcsm

import net.kigawa.mcsm.option.Option
import net.kigawa.mcsm.option.OptionStore
import net.kigawa.mcsm.util.platform.PlatFormInstance
import kotlin.system.exitProcess

class Main(
  private val platFormInstance: PlatFormInstance,
) {
  private val optionStore = OptionStore(platFormInstance)
  fun main(args: Array<String>) {
    val argList = mutableListOf(*args)

    while (argList.isNotEmpty()) {
      val arg = argList.removeAt(0)

      val opt = Option.getOption(arg)
      if (opt == null) {
        platFormInstance.logger.warning("option $arg is not found")
        help()
        exitProcess(1)
      }

      if (argList.isEmpty()) {
        platFormInstance.logger.warning("option args not enough")
        help()
        exitProcess(1)
      }
      val value = argList.removeAt(0)

      optionStore.add(opt, value)
    }
    help()
  }

  private fun help() {
    platFormInstance.logger.info("mcsm")
    platFormInstance.logger.info("----- options -----")
    Option.entries.forEach {
      platFormInstance.logger.info(
        "name: %-20s - env: %-15s - default: %-10s | ${it.description}"
          .format("${it.optName} / ${it.shortName}", it.name, it.defaultValue)
      )
    }
  }

}