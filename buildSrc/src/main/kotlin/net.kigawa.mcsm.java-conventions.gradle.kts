/*
 * This file was generated by the Gradle 'init' task.
 */
plugins {
  kotlin("multiplatform")
  id("application")
}

group = "net.kigawa"
version = "1.0.3"

repositories {
  mavenCentral()
}
dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib")
  commonTestImplementation(kotlin("test"))
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
  jvm("jvm") {
  }

  sourceSets {
    val winX64Main by getting
    val winX64Test by getting
    val linX64Main by getting
    val linX64Test by getting
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
tasks {
  test {
    useJUnitPlatform()
    testLogging {
      events("passed", "skipped", "failed")
    }
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "${JavaVersion.VERSION_17}"
    }
  }
}