package net.kigawa.mcsm.util.concurrent

import kotlinx.coroutines.*
import net.kigawa.mcsm.util.logger.KuLogger
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class Coroutines(
  private val logger: KuLogger,
) {
  companion object {
    private val defaultContext = Dispatchers.Default
    private val defaultScope
      get() = CoroutineScope(defaultContext)
    val ioContext = Dispatchers.IO
    private val ioScope
      get() = CoroutineScope(ioContext)
  }

  fun launchDefault(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
  ) = launch(defaultScope, context, start, block)

  fun <T> async(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
  ) = defaultScope.async(context, start, block)

  suspend fun <T> withContext(
    block: suspend CoroutineScope.() -> T,
  ) = withContext(defaultContext, block)

  fun launchIo(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
  ) = launch(ioScope, context, start, block)

  fun <T> asyncIo(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
  ) = ioScope.async(context, start, block)

  suspend fun <T> withContextIo(
    block: suspend CoroutineScope.() -> T,
  ) = withContext(ioContext, block)

  private fun launch(
    scope: CoroutineScope,
    context: CoroutineContext,
    start: CoroutineStart,
    block: suspend CoroutineScope.() -> Unit,
  ): Job {
    return scope.launch(context, start) {
      try {
        block()
      } catch (e: Exception) {
        if (e is CancellationException) return@launch
        logger.warning("exception in coroutine")
        logger.warning(scope.toString())
        logger.warning(e.message ?: e.toString())
        throw CoroutineLaunchException("exception in coroutine", scope, e)
      }
    }
  }
}