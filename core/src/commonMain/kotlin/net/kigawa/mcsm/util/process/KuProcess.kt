package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.io.ReaderIo
import net.kigawa.mcsm.util.io.StringLineWriterIo
import net.kigawa.mcsm.util.io.SuspendCloseable

interface KuProcess : SuspendCloseable {
  fun reader(): ReaderIo
  fun errReader(): ReaderIo
  fun writer(): StringLineWriterIo
  suspend fun waitFor()
}