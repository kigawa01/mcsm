package net.kigawa.mcsm.util.process

import net.kigawa.mcsm.util.io.ReaderIo

interface KuProcess {
  fun reader(): ReaderIo
  fun errReader(): ReaderIo
}