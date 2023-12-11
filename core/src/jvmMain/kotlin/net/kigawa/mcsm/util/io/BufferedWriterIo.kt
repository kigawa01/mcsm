package net.kigawa.mcsm.util.io

import kotlinx.coroutines.withContext
import net.kigawa.mcsm.util.concurrent.Coroutines
import java.io.BufferedWriter

class BufferedWriterIo(
  private val bufferedWriter: BufferedWriter,
) : StringLineWriterIo {
  override suspend fun writeLine(line: String) {
    withContext(Coroutines.ioContext) {
      bufferedWriter.write(line)
      bufferedWriter.newLine()
      bufferedWriter.flush()
    }
  }
}