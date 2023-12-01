package net.kigawa.mcsm.util.net

import kotlinx.coroutines.channels.Channel
import net.kigawa.mcsm.util.concurrent.Coroutines
import net.kigawa.mcsm.util.io.KuPath
import net.kigawa.mcsm.util.io.SuspendCloseable
import net.kigawa.mcsm.util.logger.KuLogger

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SocketServer(
  path: KuPath,
  logger: KuLogger?,
  coroutines: Coroutines,
) : SuspendCloseable {
  fun bind(): Channel<SocketConnection>
}