package net.kigawa.mcsm.util.io

@OptIn(ExperimentalStdlibApi::class)
interface KuCloseable : AutoCloseable {
  override fun close()
}
