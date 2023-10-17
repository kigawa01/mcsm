package net.kigawa.mcsm.util

class ContextValue<T : Any>(private val getter: () -> T) {
  fun get(): T {
    return getter.invoke()
  }
}