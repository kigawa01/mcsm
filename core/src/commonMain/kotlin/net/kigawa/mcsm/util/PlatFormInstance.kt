package net.kigawa.mcsm.util

import net.kigawa.mcsm.util.logger.KuLogger

data class PlatFormInstance(
  val logger: KuLogger,
  val getEnv: (String) -> String?,
  val exitProcess: (Int) -> Nothing,
)