package net.kigawa.mcsm.util.io

class CloseableHolderException(
  suppressed: List<Throwable>
) : RuntimeException() {
  init {
    suppressed.forEach {
      addSuppressed(it)
    }
  }
}