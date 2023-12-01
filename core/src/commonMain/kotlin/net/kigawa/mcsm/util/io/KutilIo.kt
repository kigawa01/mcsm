package net.kigawa.mcsm.util.io

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class KutilIo {
}

suspend inline fun <T> Channel<T>.forEach(func: (T) -> Unit) {
  for (item in this) {
    func(item)
  }
}

inline fun <T> Channel<T>.dispatchForEach(coroutineContext: CoroutineContext, crossinline func: (T) -> Unit) {
  CoroutineScope(coroutineContext).launch {
    for (item in this@dispatchForEach) {
      func(item)
    }
  }
}