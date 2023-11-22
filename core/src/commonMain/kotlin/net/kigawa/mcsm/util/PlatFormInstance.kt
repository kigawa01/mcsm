package net.kigawa.mcsm.util

import net.kigawa.mcsm.util.logger.KuLogger
import net.kigawa.mcsm.util.os.OSType

data class PlatFormInstance(
  val logger: KuLogger,
  val getEnv: (String) -> String?,
  val osType: OSType,
  val exitProcess: (Int) -> Nothing,
)