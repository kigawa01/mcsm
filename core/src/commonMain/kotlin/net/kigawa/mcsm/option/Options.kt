package net.kigawa.mcsm.option

import net.kigawa.mcsm.servertype.ServerTypes

enum class Options(
  val optName: String,
  val shortName: String?,
  val defaultValue: String,
) {
  SERVER_TYPE("server-type", "s", ServerTypes.PAPER.name)
}