package net.kigawa.mcsm.util.io

import kotlinx.coroutines.channels.Channel
import net.kigawa.mcsm.util.concurrent.Coroutines

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object DefaultIo {
  actual val info: WriterIo
  actual val error: WriterIo

  init {
    val infoChannel = Channel<String>()
    val errorChannel = Channel<String>()

    info = ChannelWriterIo(infoChannel)
    error = ChannelWriterIo(errorChannel)

    infoChannel.dispatchForEach(Coroutines.ioContext) {
      println(it)
    }
    errorChannel.dispatchForEach(Coroutines.ioContext) {
      System.err.println(it)
    }
  }
}