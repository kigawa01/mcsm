package net.kigawa.mcsm.servertype

import net.kigawa.mcsm.server.McServer
import net.kigawa.mcsm.server.paper.PaperServer
import net.kigawa.mcsm.server.spigot.SpigotServer
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.logger.KuLogger


enum class ServerType(
  val typeName: String,
  private val instanceCreator: (
    optionStore: OptionStore,
    logger: KuLogger,
    coroutines: Coroutines,
  ) -> McServer,
) {
  PAPER("paper", { optionStore, logger, coroutines ->
    PaperServer(optionStore, logger, coroutines)
  }),
  SPIGOT("spigot", { optionStore, logger, coroutines ->
    SpigotServer(optionStore, logger, coroutines)
  }),
  ;

  fun newServer(
    optionStore: OptionStore,
    logger: KuLogger,
    coroutines: Coroutines,
  ) = instanceCreator(optionStore, logger, coroutines)
}