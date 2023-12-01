package net.kigawa.mcsm

import kotlinx.coroutines.*
import net.kigawa.mcsm.rsync.Rsync
import net.kigawa.mcsm.servertype.ServerType
import net.kigawa.mcsm.util.io.KuCloseable
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.logger.KuLogger

class Mcsm(
  private val logger: KuLogger,
  private val rsyncPeriod: Long,
  rsyncResource: KuPath,
  rsyncTarget: KuPath,
  serverType: ServerType,
) : KuCloseable {
  private val rsync: Rsync = Rsync(logger, this, rsyncResource, rsyncTarget)
  private val setupTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    logger.info("start setup mcsm")
    rsync.copyToTarget()
    logger.info("end setup mcsm")
  }
  private val rsyncTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    logger.info("start rsync timer")
    while (isActive) {
      rsync.copyFromTarget()
      delay(rsyncPeriod * 1000)
    }
    logger.info("end rsync timer")
  }
  private val minecraftTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    val server = serverType.newServer()
    server.init()
    while (isActive) {
      server.start()
    }
  }
  private val shutdownTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    logger.info("start shutdown")
    rsyncTask.cancel()
    minecraftTask.cancel()

    rsyncTask.join()
    minecraftTask.join()

    rsync.copyFromTarget()

    logger.info("end shutdown")
  }

  fun start() {
    setupTask.start()
    rsyncTask.start()
    minecraftTask.start()
    runBlocking {
      setupTask.join()
      rsyncTask.join()
      minecraftTask.join()
      shutdownTask.join()
    }
  }

  override fun close() {
    shutdownTask.start()
  }
}