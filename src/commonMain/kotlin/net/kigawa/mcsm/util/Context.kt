package net.kigawa.mcsm.util

object Context {
  private val values = mutableListOf<ContextValue<*>>()

  fun <T : Any> set(getter: () -> T) {
    getContextValue<T>()
      ?.let { values.remove(it) }

    values.add(ContextValue(getter))
  }

  private fun <T : Any> getContextValue(): ContextValue<T>? {
    return values
      .filterIsInstance<ContextValue<T>>()
      .firstOrNull()
  }

  fun <T : Any> get(): T? {
    return getContextValue<T>()?.get()
  }
}