package net.kigawa.mcsm.util.io

interface ReaderIo : InputIo {
  fun readLine(): String
}