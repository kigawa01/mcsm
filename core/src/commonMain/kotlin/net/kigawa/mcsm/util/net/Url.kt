package net.kigawa.mcsm.util.net

import net.kigawa.mcsm.util.io.KuPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Url(
  strUrl: String,
) {
  suspend fun download(to: KuPath)
}