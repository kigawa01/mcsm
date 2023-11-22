package net.kigawa.mcsm.util.io

import java.io.BufferedReader

class BufferedReaderIo(
  private val bufferedReader: BufferedReader,
) : ReaderIo {
  override fun close() {
    bufferedReader.close()
  }

  override fun readLine(): String {
    return bufferedReader.readLine()
  }
}