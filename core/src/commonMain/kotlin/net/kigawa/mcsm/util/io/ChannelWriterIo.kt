package net.kigawa.mcsm.util.io

import kotlinx.coroutines.channels.Channel

class ChannelWriterIo(
  private val channel: Channel<String>
) : WriterIo {
  override suspend fun writeLine(line: String) {
    channel.send(line)
  }
}