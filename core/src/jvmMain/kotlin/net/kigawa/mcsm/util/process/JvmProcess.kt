package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.io.BufferedReaderIo
import net.kigawa.mcsm.util.io.ReaderIo

class JvmProcess(
  private val process: Process,
) : KuProcess {
  override fun reader(): ReaderIo {
    return BufferedReaderIo(process.inputReader())
  }

  override fun errReader(): ReaderIo {
    return BufferedReaderIo(process.errorReader())
  }
}