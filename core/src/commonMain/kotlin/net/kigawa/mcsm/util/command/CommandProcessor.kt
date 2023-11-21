package net.kigawa.mcsm.util.command

import net.kigawa.mcsm.util.logger.KuLogger

class CommandProcessor(
  executor: CommandExecutor?,
  private val logger: KuLogger,
) : CommandItem(executor, logger) {
  private val executor: CommandExecutorWrapper?

  init {
    this.executor = executor?.let { CommandExecutorWrapper(it) }
  }

  fun execute(commands: MutableList<String>) {
    if (commands.isEmpty()) {
      executor?.execute() ?: help()
      return
    }
    val command = commands.removeAt(0)

  }

  private fun help() {
    // TODO:
  }

}