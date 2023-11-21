package net.kigawa.mcsm.util.command

import net.kigawa.mcsm.util.logger.KuLogger

abstract class CommandItem(
  executor: CommandExecutor?,
  private val logger: KuLogger,
) {
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