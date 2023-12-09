package net.kigawa.mcsm

import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import net.kigawa.mcsm.servertype.ServerType
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
  private val logger: KuLogger,
  private val coroutines: Coroutines,
  private val rsyncPeriod: Long,
  private val rsyncResource: KuPath,
  private val rsyncTarget: KuPath,
  private val socket: KuPath,
  private val server: ServerType,
  private val optionStore: OptionStore,
) {
  fun start() {
    val mcsm = Mcsm(logger, rsyncPeriod, rsyncResource, rsyncTarget, server, coroutines, optionStore)

    val main = coroutines.launchDefault {
      mcsm.start()
    }
    val socket = coroutines.launchIo {
      CloseableHolder.trySuspendResource {
        add(mcsm)
        socket.parent().createDirOrGet()
        val server = SocketServer(socket, logger, coroutines).also { add(it) }
        val channel = server.bind()
        for (con in channel) {
          readCmd(con, mcsm, server)
        }
      }
    }
    runBlocking {
      main.join()
      socket.join()
    }
  }

  private suspend fun readCmd(con: SocketConnection, mcsm: Mcsm, socketServer: SocketServer) {
    con.tryCatchSuspend {
      val writer = con.writer()
      for (line in con.reader()) {
        when (line) {
          "stop" -> {
            mcsm.suspendClose()
            writer.send("shutdown mcsm")
            con.close()
            socketServer.close()
          }

          else -> {
            logger.warning("command '$line' not found")
            writer.send("command '$line' not found")
            con.close()
          }
        }
      }
    }
  }

  fun stop() {
    val socket = coroutines.launchIo {
      CloseableHolder.trySuspendResource {
        val client = SocketClient(socket, logger, coroutines).also { add(it) }
        val con = client.connect().also { add(it) }
        logger.fine("connected socket")
        val writer = con.writer()
        writer.send("stop")
        for (line in con.reader()) {
          logger.info(line)
          if (line == "shutdown mcsm") con.suspendClose()
        }
      }
      cancel()
    }
    runBlocking {
      socket.join()
    }
  }
}