package net.kigawa.mcsm.util.io

interface WriterIo {
  suspend fun writeLine(line: String)
}