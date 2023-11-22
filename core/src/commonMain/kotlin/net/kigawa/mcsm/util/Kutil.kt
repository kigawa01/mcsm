package net.kigawa.mcsm.util

object Kutil {
  fun fillStr(src: String, size: Int, fillChar: Char = ' '): String {
    val sb = StringBuilder(size)
    for (i in 0..<size) {
      sb.append(src.getOrNull(i) ?: fillChar)
    }
    return sb.toString()
  }
}