package net.kigawa.mcsm.util.io

import net.kigawa.mcsm.util.tryCatch

interface ReaderIo : InputIo {
  fun readLine(): String?
  fun tryReadContinue(func: (String) -> Unit) = tryCatch {
    readContinue(func)
  }


  fun readContinue(func: (String) -> Unit) {
    while (true) {
      val line: String = readLine() ?: return
      func(line)
    }
  }
}