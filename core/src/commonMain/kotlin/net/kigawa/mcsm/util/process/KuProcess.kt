package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.KuCloseable
import net.kigawa.mcsm.util.io.ReaderIo

interface KuProcess : KuCloseable {
  fun reader(): ReaderIo
  fun errReader(): ReaderIo
  suspend fun waitFor()
}