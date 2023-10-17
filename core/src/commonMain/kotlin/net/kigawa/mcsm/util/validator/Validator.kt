package net.kigawa.mcsm.util.validator

interface Validator<ORIGINAL : Any?, FROM : Any?, TO : Any?> {
  fun validate(value: ORIGINAL): TO

}