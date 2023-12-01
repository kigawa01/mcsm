package net.kigawa.mcsm.util.logger

import java.util.logging.Level


fun LogLevel.javaLevel(): Level? {
  return Level.parse(this.name)
}