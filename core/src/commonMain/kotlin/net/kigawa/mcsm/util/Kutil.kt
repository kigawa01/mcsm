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
inline fun <T, R, reified E : Throwable> T.tryCatch(
  block: (self: T) -> R,
  catch: (self: T, e: E) -> R = { _, e -> throw e },
  finally: (self: T, e: E?, result: R?) -> Unit = { self, _, _ ->
    if (self is KuCloseable) self.close()
    if (self is AutoCloseable) self.close()
  },
): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  var exception: E? = null
  var result: R? = null
  try {
    return block(this).also { result = it }

  } catch (e: Throwable) {
    if (e is E) return e
      .also { exception = it }
      .let { catch(this, it) }
      .also { result = it }
    throw e

  } finally {
    finally(this, exception, result)
  }
}

inline fun <T, R> T.tryCatch(block: (self: T) -> R): R = tryCatch<T, R, Throwable>(block)