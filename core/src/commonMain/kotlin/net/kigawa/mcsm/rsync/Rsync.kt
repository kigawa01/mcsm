package net.kigawa.mcsm.rsync

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import net.kigawa.mcsm.Option
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.logger.KuLogger
import net.kigawa.mcsm.util.process.KuProcessBuilder
import net.kigawa.mcsm.util.tryCatch

class Rsync(
  private val optionStore: OptionStore,
  private val logger: KuLogger,
) {
  suspend fun copyToTarget() = copy(
    optionStore.get(Option.RSYNC_RESOURCE),
    optionStore.get(Option.RSYNC_TARGET)
  )

  suspend fun copyFromTarget() = copy(
    optionStore.get(Option.RSYNC_TARGET),
    optionStore.get(Option.RSYNC_RESOURCE),
  )

  private suspend fun copy(from: String, to: String) = KuProcessBuilder(
    "rsync",
    "-rq",
    "--del",
    from,
    to
  )
    .start()
    .tryCatch { process ->
      logger.info("start rsync")
      CoroutineScope(Dispatchers.IO).launch {
        process.errReader().tryReadContinue {
          logger.warning(it)
        }
      }
      CoroutineScope(Dispatchers.IO).launch {
        process.reader().tryReadContinue {
          logger.info(it)
        }
      }
      process.waitFor()
      logger.info("end rsync")
    }
}

