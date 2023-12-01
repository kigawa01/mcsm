package net.kigawa.mcsm

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import net.kigawa.mcsm.rsync.Rsync
import net.kigawa.mcsm.servertype.ServerType
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable
import net.kigawa.mcsm.util.logger.KuLogger

class Mcsm(
  private val logger: KuLogger,
  private val rsyncPeriod: Long,
  rsyncResource: KuPath,
  rsyncTarget: KuPath,
  serverType: ServerType,
  private val coroutines: Coroutines,
  optionStore: OptionStore,
) : SuspendCloseable {
  private val rsync: Rsync = Rsync(logger, this, rsyncResource, rsyncTarget)
  private val setupTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    logger.info("start setup mcsm")
    rsync.copyToTarget()
    logger.info("end setup mcsm")
  }
  private val rsyncTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    logger.info("start rsync timer")
    setupTask.join()
    while (isActive) {
      rsync.copyFromTarget()
      delay(rsyncPeriod * 1000)
    }
    logger.info("end rsync timer")
  }
  private var isShutdown = false
  private val server = serverType.newServer(optionStore, logger, coroutines)
  private val minecraftTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    val countChannel = Channel<Boolean>()
    val delayFlow = MutableStateFlow(0L)
    val countTask = coroutines.launchDefault {
      var count = 1L
      var timer = 180
      do {
        if (countChannel.receiveCatching().getOrNull() != null) {
          count *= 2
          timer = 180
          delayFlow.emit(count)
          continue
        }
        timer--
        delay(1000)
      } while (timer > 1 && isActive)
    }

    server.init()
    setupTask.join()
    while (isActive && !isShutdown) {
      server.start()
      delay(delayFlow.value)
      countChannel.send(true)
      countTask.start()
    }
    countTask.cancel()
  }
  private val shutdownTask = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
    logger.info("start shutdown")
    isShutdown = true
    rsyncTask.cancel()
    server.close()

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

  override suspend fun suspendClose() {
    shutdownTask.start()
    shutdownTask.join()
  }
}