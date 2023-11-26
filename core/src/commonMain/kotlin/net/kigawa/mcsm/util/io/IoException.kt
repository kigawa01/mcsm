package net.kigawa.mcsm.util.io

open class IoException(message: String?) : Exception(message) {
}

expect fun ioException(message: String?): IoException