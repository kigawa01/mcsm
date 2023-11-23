package net.kigawa.mcsm

import net.kigawa.mcsm.servertype.ServerTypes

enum class Option(
  val optName: String,
  val shortName: String?,
  val description: String,
  val defaultValue: String,
) {
  SERVER_TYPE("server-type", "s", "minecraft server type", ServerTypes.PAPER.name),
  RSYNC_RESOURCE(
    "resource", "r", "rsync resource", "resource"
  ),
  RSYNC_TARGET("target", "t", "rsync target", "target"),
  ;

  companion object {
    fun getOption(name: String): Option? {
      return entries.firstOrNull {
        if (name.startsWith("--") && "--${it.optName}" == name) return@firstOrNull true
        if (name.startsWith("-") && "-${it.shortName}" == name) return@firstOrNull true

        return@firstOrNull false
      }
    }
  }

}