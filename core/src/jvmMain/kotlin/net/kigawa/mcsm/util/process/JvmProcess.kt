package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.io.BufferedReaderIo
import net.kigawa.mcsm.util.io.ReaderIo
import java.io.BufferedReader
import java.io.InputStreamReader

class JvmProcess(
  private val process: Process,
) : KuProcess {
  override fun reader(): ReaderIo {
    return BufferedReaderIo(BufferedReader(InputStreamReader(process.inputStream)))
  }
}