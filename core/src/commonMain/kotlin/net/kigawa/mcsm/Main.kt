package net.kigawa.mcsm

import net.kigawa.mcsm.util.Kutil
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.PlatFormInstance
import net.kigawa.mcsm.util.logger.ConsoleLoggerHandler
import net.kigawa.mcsm.util.logger.KuLogger

class Main(
  private val platFormInstance: PlatFormInstance,
) {
  private val optionStore = OptionStore()
  private val logger = KuLogger(Mcsm::class, listOf(ConsoleLoggerHandler()))
  private val command = Command(optionStore, logger)
  fun main(args: Array<String>) {
    logger.info("hello mcsm")
    val argList = mutableListOf(*args)
    val cmd = getNextCommand(argList)
    if (cmd == null) {
      logger.warning("need command arg")
      platFormInstance.exitProcess(1)
    }
    getNextCommand(argList)?.let {
      logger.warning("command arg '$it' is not found")
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
        logger.warning("option $arg is not found")
        help()
        platFormInstance.exitProcess(1)
      }

      if (argList.isEmpty()) {
        logger.warning("option args not enough")
        help()
        platFormInstance.exitProcess(1)
      }
      val value = argList.removeAt(0)

      optionStore.add(opt, value)
    }
    return null
  }

  private fun help() {
    logger.info("mcsm")
    logger.info("----- options -----")
    Option.entries.forEach {
      logger.info(
        "name: ${Kutil.fillStr("{$it.optName} / ${it.shortName}", 20)}" + " - env: ${
          Kutil.fillStr(
            it.name, 15
          )
        }" + " - default: ${Kutil.fillStr(it.defaultValue, 10)}" + " | ${it.description}"
      )
    }
  }

}