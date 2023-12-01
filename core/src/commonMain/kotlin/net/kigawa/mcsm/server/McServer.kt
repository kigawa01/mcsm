package net.kigawa.mcsm.server

import net.kigawa.mcsm.util.io.SuspendCloseable

interface McServer : SuspendCloseable {
  suspend fun init()
  suspend fun start()
}