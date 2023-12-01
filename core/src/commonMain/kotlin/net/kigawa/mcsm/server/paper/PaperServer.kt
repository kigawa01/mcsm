package net.kigawa.mcsm.server.paper

import net.kigawa.mcsm.Option
import net.kigawa.mcsm.server.McServer
import net.kigawa.mcsm.server.McVersion
import net.kigawa.mcsm.util.OptionStore
import net.kigawa.mcsm.util.Paths
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.DefaultIo
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.StringLineWriterIo
import net.kigawa.mcsm.util.logger.KuLogger
import net.kigawa.mcsm.util.process.KuProcessBuilder

class PaperServer(
  optionStore: OptionStore,
  private val logger: KuLogger,
  private val coroutines: Coroutines,
) : McServer {
  private val mcVersion = McVersion.fromVersion(optionStore.get(Option.SERVER_VERSION))
  private val paperVersion = PaperVersion.fromMcVersion(mcVersion)
  private val jarPath = KuPath(optionStore.get(Option.BUILD_DIR)).join("paper-${mcVersion.version}.jar")
  private val serverDIr = KuPath(optionStore.get(Option.SERVER_DIR))
  private var serverConsole: StringLineWriterIo? = null

  override suspend fun init() {
    if (!jarPath.isExists()) {
      jarPath.parent().createDirOrGet()
      logger.info("download jar to ${jarPath.toAbsolute().strPath()}")
      paperVersion.url.download(jarPath)
    }
  }

  override suspend fun start() {
    val process = KuProcessBuilder(
      Paths.JAVA.path.strPath(),
      "-jar",
      "-Xmx6G",
      "-server",
      jarPath.toAbsolute().strPath(),
      "nogui"
    )
      .workDir(serverDIr.createDirOrGet())
      .start()
    serverConsole = process.writer()
    val info = coroutines.launchIo {
      process.reader().tryReadContinue {
        DefaultIo.out.writeLine(it)
      }
    }
    val err = coroutines.launchIo {
      process.errReader().tryReadContinue {
        DefaultIo.error.writeLine(it)
      }
    }
    info.join()
    err.join()
  }

  override suspend fun suspendClose() {
    serverConsole?.writeLine("stop")
  }
}