package net.kigawa.mcsm.option

import net.kigawa.mcsm.util.platform.PlatFormInstance

class OptionStore(
  private val platFormInstance: PlatFormInstance,
) {
  private val options = mutableMapOf<Option, String>()

  @Synchronized
  fun add(option: Option, value: String) {
    options[option] = value
  }

  @Synchronized
  fun get(option: Option): String = options[option]
    ?: platFormInstance.getEnv(option.name)
    ?: option.defaultValue

}