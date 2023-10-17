package net.kigawa.mcsm.util.commanopt

import net.kigawa.mcsm.util.validator.Validator

interface Option<T : Any?> {
  val optName: String
  val shortName: String?
  val defaultValue: String
  val validator: Validator<String, *, T>?
}