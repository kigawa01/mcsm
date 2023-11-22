package net.kigawa.mcsm.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

object Kutil {
  fun fillStr(src: String, size: Int, fillChar: Char = ' '): String {
    val sb = StringBuilder(size)
    for (i in 0..<size) {
      sb.append(src.getOrNull(i) ?: fillChar)
    }
    return sb.toString()
  }

}

@OptIn(ExperimentalContracts::class, ExperimentalStdlibApi::class)
inline fun <T, R> T.tryCatch(block: (T) -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  try {
    return block(this)
  } catch (e: Throwable) {
    throw e
  } finally {
    if (this is KuCloseable) close()
    if (this is AutoCloseable) close()
  }
}