package net.kigawa.mcsm.util.io

import kotlinx.coroutines.runBlocking

interface SuspendCloseable : KuCloseable {
  suspend fun suspendClose()
  override fun close() {
    runBlocking { suspendClose() }
  }
}