package net.kigawa.mcsm

import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.CloseableHolder
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.logger.KuLogger
import net.kigawa.mcsm.util.net.SocketClient
import net.kigawa.mcsm.util.net.SocketConnection
import net.kigawa.mcsm.util.net.SocketServer
import net.kigawa.mcsm.util.tryCatchSuspend

class Command(
  private val optionStore: OptionStore,
  private val logger: KuLogger,
) {
  private val socket = optionStore.get(Option.SOCKET)
  fun start() {
    val mcsm = Mcsm(logger, optionStore)

    val main = Coroutines.launch {
      mcsm.start()
    }
    val socket = Coroutines.launchIo {
      CloseableHolder.trySuspendResource {
        val server = SocketServer(KuPath(socket)).also { add(it) }
        val channel = server.bind()
        for (con in channel) {
          readCmd(con, mcsm)
        }
      }
    }
    runBlocking {
      main.join()
      socket.join()
    }
  }

  private suspend fun readCmd(con: SocketConnection, mcsm: Mcsm) {
    con.tryCatchSuspend {
      val writer = con.writer()
      for (line in con.reader()) {
        when (line) {
          "stop" -> mcsm.shutdown()
          else -> writer.send("command '$line' not found")
        }
      }
    }
  }

  fun stop() {
    val socket = Coroutines.launchIo {
      CloseableHolder.trySuspendResource {
        val client = SocketClient(KuPath(socket)).also { add(it) }
        val con = client.connect().also { add(it) }
        val writer = con.writer()
        writer.send("stop")
        for (line in con.reader()) {
          logger.info(line)
        }
      }
      cancel()
    }
    runBlocking {
      socket.join()
    }
  }
}