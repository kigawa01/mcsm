package net.kigawa.mcsm.util.io

interface StringLineWriterIo {
  suspend fun writeLine(line: String)
}