package net.kigawa.mcsm.util

import net.kigawa.mcsm.util.io.KuCloseable
import net.kigawa.mcsm.util.io.SuspendCloseable
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

@OptIn(ExperimentalContracts::class)
inline fun <T : KuCloseable, R> T.tryCatch(
  block: (self: T) -> R,
): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  try {
    return block(this)
  } catch (e: Throwable) {
    throw e
  } finally {
    close()
  }
}

@OptIn(ExperimentalContracts::class)
suspend inline fun <T : KuCloseable, R> T.tryCatchSuspend(
  block: (self: T) -> R,
): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  try {
    return block(this)
  } catch (e: Throwable) {
    throw e
  } finally {
    when (this) {
      is SuspendCloseable -> suspendClose()
      else -> close()
    }
  }
}