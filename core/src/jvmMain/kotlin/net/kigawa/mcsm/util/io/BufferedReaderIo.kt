package net.kigawa.mcsm.util.io

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader

class BufferedReaderIo(
  private val bufferedReader: BufferedReader,
) : ReaderIo {

  override fun readLine(): String? {
    return bufferedReader.readLine()
  }

  override fun close() {
    bufferedReader.close()
  }

  override suspend fun suspendClose() {
    withContext(Dispatchers.IO) {
      bufferedReader.close()
    }
  }
}