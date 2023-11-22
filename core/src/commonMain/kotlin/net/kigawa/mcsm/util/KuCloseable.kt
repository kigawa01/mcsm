package net.kigawa.mcsm.util

@OptIn(ExperimentalStdlibApi::class)
interface KuCloseable : AutoCloseable {
  override fun close()
}

