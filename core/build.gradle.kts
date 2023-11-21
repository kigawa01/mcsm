plugins {
  id("net.kigawa.mcsm.java-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
  id("application")
}

kotlin {
//  listOf(
//    mingwX64("winX64"),
//    linuxX64("linX64"),
//  ).forEach {
//    it.apply {
//      binaries {
//        executable {
//          entryPoint = "main"
//        }
//      }
//    }
  jvm("jvm") {
    application {
      mainClass.set("net.kigawa.mcsm.MainKt")
    }
  }

  sourceSets {
//    val winX64Main by getting
//    val winX64Test by getting
//    val linX64Main by getting
//    val linX64Test by getting
    val jvmMain by getting {
      dependencies {
        implementation("net.kigawa.kutil:log:2.0")
      }
    }
    val jvmTest by getting
    val commonMain by getting
    val commonTest by getting
  }
}

