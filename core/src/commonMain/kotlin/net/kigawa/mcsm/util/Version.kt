package net.kigawa.mcsm.util

class Version(
  private val strVersion: String,
) {
  fun isAfter(before: Version): Boolean {
    val ownIntVer = intVersion()
    before.intVersion().forEachIndexed { index, value ->
      val own = ownIntVer.getOrNull(index) ?: return false
      if (own == value) return@forEachIndexed
      return own > value
    }
    return false
  }

  fun intVersion(): List<Int> {
    val result = mutableListOf<Int>()
    var section = ""
    strVersion.forEach {
      if (it.code in 48..57) {
        section += it
        return@forEach
      }
      if (section == "") return@forEach
      result.add(section.toInt())
      section = ""
    }
    if (section != "") result.add(section.toInt())
    return result
  }

  fun split(): List<String> {
    return strVersion.split(".")
  }
}
