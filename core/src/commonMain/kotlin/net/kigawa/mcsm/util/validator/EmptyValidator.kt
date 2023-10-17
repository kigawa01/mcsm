package net.kigawa.mcsm.util.validator

class EmptyValidator<T : Any?> : Validator<T, T, T> {
  override fun validate(value: T): T {
    return value
  }
}