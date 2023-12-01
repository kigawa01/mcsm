package net.kigawa.mcsm.server.paper

import net.kigawa.mcsm.server.McVersion
import net.kigawa.mcsm.util.net.Url

enum class PaperVersion(
  val url: Url,
) {
  V1_19_4(
    Url("https://api.papermc.io/v2/projects/paper/versions/1.19.4/builds/550/downloads/paper-1.19.4-550.jar")
  );

  companion object {
    fun fromMcVersion(mcVersion: McVersion): PaperVersion {
      return valueOf(mcVersion.name)
    }
  }
}