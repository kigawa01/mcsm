package net.kigawa.mcsm.util.process

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.kigawa.mcsm.util.io.BufferedReaderIo
import net.kigawa.mcsm.util.io.BufferedWriterIo
import net.kigawa.mcsm.util.io.ReaderIo
import net.kigawa.mcsm.util.io.StringLineWriterIo

class JvmProcess(
  private val process: Process,
) : KuProcess {
  override fun reader(): ReaderIo {
    return BufferedReaderIo(process.inputReader())
  }

  override fun errReader(): ReaderIo {
    return BufferedReaderIo(process.errorReader())
  }

  override suspend fun waitFor() {
    withContext(Dispatchers.IO) {
      process.waitFor()
    }
  }

  override suspend fun suspendClose() {
    process.destroy()
  }

  override fun writer(): StringLineWriterIo {
    return BufferedWriterIo(process.outputWriter())
  }
}