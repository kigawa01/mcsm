plugins {
  id("net.kigawa.mcsm.java-conventions")
}
dependencies {
  commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
val mainClassName = "net.kigawa.mcsm.MainKt"
kotlin {

  afterEvaluate {
    tasks {

      named("jvmJar", Jar::class) {
        duplicatesStrategy = DuplicatesStrategy.WARN

        val classpath = configurations.getByName("jvmRuntimeClasspath")
          .map { if (it.isDirectory) it else zipTree(it) }
        from(classpath) {
          exclude("META-INF/*.SF")
          exclude("META-INF/*.DSA")
          exclude("META-INF/*.RSA")
        }

        manifest {
          attributes(
            "Main-Class" to mainClassName,
            "Built-By" to System.getProperty("user.name"),
            "Build-Jdk" to System.getProperty("java.version"),
            "Implementation-Version" to project.version,
            "Created-By" to "Gradle v${GradleVersion.current()}",
          )
        }

        inputs.property("mainClassName", mainClassName)
        inputs.files(classpath)
      }
    }
  }
}

