package net.kigawa.mcsm.util.net

import kotlinx.coroutines.withContext
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.KuPath
import java.io.FileOutputStream
import java.net.URI
import java.nio.channels.Channels

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Url actual constructor(
  strUrl: String,
) {
  private val nativeUri = URI(strUrl)
  actual suspend fun download(to: KuPath) {
    withContext(Coroutines.ioContext) {
      nativeUri.toURL().openStream().let {
        Channels.newChannel(it)
      }.use { channel ->
        FileOutputStream(to.toFile().nativeFile).use {
          it.channel.transferFrom(channel, 0, Long.MAX_VALUE)
        }
      }
    }

  }


}