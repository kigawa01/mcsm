package net.kigawa.mcsm.util

import kotlin.test.Test
import kotlin.test.assertEquals

class VersionTest {
  companion object {
    private val v1_1_1 = Version("v1.1.1")
    private val v1_1_2 = Version("v1.1.2")
    private val v1_2_2 = Version("v1.2.2")
    private val v2_2_2 = Version("v2.2.2")
    private val v20_20_20 = Version("v20.20.20")
  }

  @Test
  fun intVersion() {
    assertEquals(listOf(1, 1, 1), v1_1_1.intVersion())
    assertEquals(listOf(20, 20, 20), v20_20_20.intVersion())
  }
}