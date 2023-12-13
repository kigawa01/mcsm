package net.kigawa.mcsm.server.spigot

import kotlinx.coroutines.CoroutineStart
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
import net.kigawa.mcsm.util.net.Url
import net.kigawa.mcsm.util.process.KuProcessBuilder

class SpigotServer(
  optionStore: OptionStore,
  private val logger: KuLogger,
  private val coroutines: Coroutines,
) : McServer {
  private val mcVersion = McVersion.fromVersion(optionStore.get(Option.SERVER_VERSION))
  private val buildDIr = KuPath(optionStore.get(Option.BUILD_DIR))
  private val jarPath = buildDIr.join("spigot-${mcVersion.version}.jar")
  private val buildToolPath = buildDIr.join("BuildTools.jar")
  private val serverDIr = KuPath(optionStore.get(Option.SERVER_DIR))
  private var serverConsole: StringLineWriterIo? = null
  private val serverJob = coroutines.launchDefault(start = CoroutineStart.LAZY) {
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
      process.suspendClose()
    }
    val err = coroutines.launchIo {
      process.errReader().tryReadContinue {
        DefaultIo.error.writeLine(it)
      }
      process.suspendClose()
    }
    process.waitFor()
    serverConsole = null
    process.suspendClose()
  }

  override suspend fun init() {
    if (!jarPath.isExists()) {
      buildDIr.createDirOrGet()
      buildToolPath.removeIfExists()
      logger.info("download jar to ${buildToolPath.toAbsolute().strPath()}")
      Url("https://hub.spigotmc.org/jenkins/job/BuildTools/lastStableBuild/artifact/target/BuildTools.jar")
        .download(buildToolPath)
      val process = KuProcessBuilder(
        Paths.JAVA.path.strPath(),
        "-jar",
        "-Xmx6G",
        "-server",
        buildToolPath.toAbsolute().strPath()
      )
        .workDir(buildDIr.createDirOrGet())
        .start()
      coroutines.launchIo {
        process.reader().tryReadContinue {
          DefaultIo.out.writeLine(it)
        }
        process.suspendClose()
      }
      coroutines.launchIo {
        process.errReader().tryReadContinue {
          DefaultIo.error.writeLine(it)
        }
        process.suspendClose()
      }
      process.waitFor()
      process.suspendClose()
    }
  }

  override suspend fun start() {
    serverJob.start()
    serverJob.join()
  }

  override suspend fun suspendClose() {
    serverConsole?.writeLine("stop")
    serverJob.join()
  }
}