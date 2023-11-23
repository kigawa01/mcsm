package net.kigawa.mcsm.util.io

abstract class IoException(message: String?) : Exception(message) {
}

expect fun ioException(message: String?): IoException