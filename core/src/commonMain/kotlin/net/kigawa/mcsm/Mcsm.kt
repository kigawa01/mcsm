package net.kigawa.mcsm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.kigawa.mcsm.rsync.Rsync
import net.kigawa.mcsm.util.SignalHandler

class Mcsm(
  private val rsync: Rsync,
) {
  private val setupTask = CoroutineScope(Dispatchers.Default).launch {
    rsync.copyToTarget()
  }
  private val rsyncTask = CoroutineScope(Dispatchers.Default).launch {
    while (isActive) {
      rsync.copyFromTarget()
    }
  }
  private val minecraftTask = CoroutineScope(Dispatchers.Default).launch {}
  private val shutdownTask = CoroutineScope(Dispatchers.Default).launch {
    rsyncTask.cancel()
    rsyncTask.join()
    minecraftTask.join()
    rsync.copyFromTarget()
  }

  fun start() {
    SignalHandler.shutdownHook {
      shutdownTask.start()
    }
    setupTask.start()
    rsyncTask.start()
    minecraftTask.start()
  }
}