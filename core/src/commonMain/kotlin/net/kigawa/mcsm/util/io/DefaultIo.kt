package net.kigawa.mcsm.util.io

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object DefaultIo {
  fun printErr(message: String)
}