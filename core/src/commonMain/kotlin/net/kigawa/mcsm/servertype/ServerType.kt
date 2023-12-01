package net.kigawa.mcsm.servertype

import net.kigawa.mcsm.server.McServer
import net.kigawa.mcsm.server.paper.PaperServer


enum class ServerType(
  val typeName: String,
  private val instanceCreator: () -> McServer,
) {
  PAPER("paper", { PaperServer() }),
  ;

  fun newServer() = instanceCreator()
}