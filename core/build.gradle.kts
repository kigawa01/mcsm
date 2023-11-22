plugins {
  id("net.kigawa.mcsm.java-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}
dependencies {
  commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}