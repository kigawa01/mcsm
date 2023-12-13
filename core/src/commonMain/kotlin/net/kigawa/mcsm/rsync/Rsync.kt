package net.kigawa.mcsm.rsync

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import net.kigawa.mcsm.Mcsm
import net.kigawa.mcsm.util.io.IoException
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.logger.KuLogger
import net.kigawa.mcsm.util.process.KuProcessBuilder

class Rsync(
  private val logger: KuLogger,
  private val mcsm: Mcsm,
  private val rsyncResource: KuPath,
  private val rsyncTarget: KuPath,
) {
  suspend fun copyToTarget() = copy(rsyncResource, rsyncTarget)

  suspend fun copyFromTarget() = copy(rsyncTarget, rsyncResource)

  private suspend fun copy(from: KuPath, to: KuPath) {
    val process = try {
      KuProcessBuilder(
        "rsync", "-rq", "--delete",
        "${from.strPath().removeSuffix(KuPath.separator)}/",
        "${to.strPath().removeSuffix(KuPath.separator)}/",
      ).start()

    } catch (e: IoException) {
      logger.warning("execute rsync failed")
      e.message?.let { logger.warning(it) }
      return
    }
    try {
      logger.fine("start rsync")
      CoroutineScope(Dispatchers.IO).launch {
        process.errReader().tryReadContinue {
          logger.warning(it)
        }
      }
      CoroutineScope(Dispatchers.IO).launch {
        process.reader().tryReadContinue {
          logger.fine(it)
        }
      }
      process.waitFor()
      logger.fine("end rsync")
    } finally {
      process.close()
    }
  }
}

