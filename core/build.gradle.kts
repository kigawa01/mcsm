plugins {
  id("net.kigawa.mcsm.java-conventions")
}

kotlin {
  listOf(
    mingwX64("winX64"),
    linuxX64("linX64"),
  ).forEach {
    it.apply {
      binaries {
        executable {
          entryPoint = "main"
        }
      }
    }
  }

  sourceSets {
    val winX64Main by getting
    val winX64Test by getting
    val linX64Main by getting
    val linX64Test by getting
    val commonMain by getting
    val commonTest by getting
  }
}
