package net.kigawa.mcsm.util.context

class ContextValue<T : Any>(private val getter: () -> T) {
  fun get(): T {
    return getter.invoke()
  }
}