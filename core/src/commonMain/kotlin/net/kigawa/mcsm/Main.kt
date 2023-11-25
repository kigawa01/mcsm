package net.kigawa.mcsm

import net.kigawa.mcsm.util.Kutil
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.PlatFormInstance

class Main(
  private val platFormInstance: PlatFormInstance,
) {
  private val optionStore = OptionStore()
  private val command = Command(optionStore, platFormInstance.logger)
  fun main(args: Array<String>) {
    platFormInstance.logger.info("start mcsm")
    val argList = mutableListOf(*args)
    val cmd = getNextCommand(argList)
    if (cmd == null) {
      platFormInstance.logger.warning("need command arg")
      platFormInstance.exitProcess(1)
    }
    getNextCommand(argList)?.let {
      platFormInstance.logger.warning("command arg '$it' is not found")
      platFormInstance.exitProcess(1)
    }
    if (cmd.lowercase() == "start") command.start()
    if (cmd.lowercase() == "stop") command.stop()
  }

  private fun getNextCommand(argList: MutableList<String>): String? {
    while (argList.isNotEmpty()) {
      val arg = argList.removeFirst()

      if (!arg.startsWith("-")) return arg

      val opt = Option.getOption(arg)
      if (opt == null) {
        platFormInstance.logger.warning("option $arg is not found")
        help()
        platFormInstance.exitProcess(1)
      }

      if (argList.isEmpty()) {
        platFormInstance.logger.warning("option args not enough")
        help()
        platFormInstance.exitProcess(1)
      }
      val value = argList.removeAt(0)

      optionStore.add(opt, value)
    }
    return null
  }

  private fun help() {
    platFormInstance.logger.info("mcsm")
    platFormInstance.logger.info("----- options -----")
    Option.entries.forEach {
      platFormInstance.logger.info(
        "name: ${Kutil.fillStr("{$it.optName} / ${it.shortName}", 20)}" + " - env: ${
          Kutil.fillStr(
            it.name, 15
          )
        }" + " - default: ${Kutil.fillStr(it.defaultValue, 10)}" + " | ${it.description}"
      )
    }
  }

}