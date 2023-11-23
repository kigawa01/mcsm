package net.kigawa.mcsm

import net.kigawa.mcsm.rsync.Rsync
import net.kigawa.mcsm.util.Kutil
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.PlatFormInstance

class Main(
  private val platFormInstance: PlatFormInstance,
) {
  private val optionStore = OptionStore(platFormInstance)
  private val rsync = Rsync(optionStore, platFormInstance.logger)
  private val mcsm = Mcsm(rsync)
  fun main(args: Array<String>) {
    val argList = mutableListOf(*args)

    while (argList.isNotEmpty()) {
      val arg = argList.removeAt(0)

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
    mcsm.start()
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