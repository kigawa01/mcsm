package net.kigawa.mcsm.util.io

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class CloseableHolder(closeableList: List<KuCloseable>) : SuspendCloseable {
  private val closeableList = closeableList.toMutableList()

  companion object {
    @OptIn(ExperimentalContracts::class)
    inline fun <R> tryResource(closeableList: List<KuCloseable> = listOf(), func: CloseableHolder.() -> R): R {
      contract {
        callsInPlace(func, InvocationKind.EXACTLY_ONCE)
      }
      val closeableHolder = CloseableHolder(closeableList)
      try {
        return closeableHolder.func()
      } finally {
        closeableHolder.close()
      }
    }

    @OptIn(ExperimentalContracts::class)
    suspend inline fun <R> trySuspendResource(
      closeableList: List<KuCloseable> = listOf(), func: CloseableHolder.() -> R,
    ): R {
      contract {
        callsInPlace(func, InvocationKind.EXACTLY_ONCE)
      }
      val closeableHolder = CloseableHolder(closeableList)
      try {
        return closeableHolder.func()
      } finally {
        closeableHolder.suspendClose()
      }
    }
  }

  fun <T : KuCloseable> add(closeable: T): T {
    closeableList.add(closeable)
    return closeable
  }

  @OptIn(ExperimentalStdlibApi::class)
  fun <T : AutoCloseable> add(closeable: T): T {
    closeableList.add(object : KuCloseable {
      override fun close() {
        closeable.close()
      }
    })
    return closeable
  }

  override fun close() {
    val exceptions = mutableListOf<Throwable>()
    while (true) {
      val closeable = closeableList.removeFirstOrNull() ?: break
      try {
        closeable.close()
      } catch (e: Throwable) {
        exceptions.add(e)
      }
    }
    if (exceptions.isEmpty()) return
    throw CloseableHolderException(exceptions)
  }

  override suspend fun suspendClose() {
    val exceptions = mutableListOf<Throwable>()
    while (true) {
      val closeable = closeableList.removeFirstOrNull() ?: break
      try {
        if (closeable is SuspendCloseable) closeable.suspendClose()
        else closeable.close()
      } catch (e: Throwable) {
        exceptions.add(e)
      }
    }
    if (exceptions.isEmpty()) return
    throw CloseableHolderException(exceptions)
  }
}