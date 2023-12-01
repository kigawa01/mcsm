package net.kigawa.mcsm.server

enum class McVersion(
  val version: String,
) {
  V1_19_4("1.19.4")
  ;

  companion object {
    fun fromVersion(version: String): McVersion {
      return McVersion.entries.first {
        it.version == version
      }
    }
  }
}