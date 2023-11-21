package net.kigawa.mcsm.util.command

import net.kigawa.mcsm.util.logger.KuLogger

class CommandProcessor(
  executor: CommandExecutor?,
  private val logger: KuLogger,
) : CommandItem(executor, logger) {

}