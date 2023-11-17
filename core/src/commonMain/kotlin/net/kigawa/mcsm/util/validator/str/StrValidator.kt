package net.kigawa.mcsm.util.validator.str

import net.kigawa.mcsm.util.validator.Validator

class StrValidator<ORIGINAL : Any?, FROM : Any?>(parent: Validator<ORIGINAL, *, FROM>) :
  AbstractStrValidator<ORIGINAL, FROM>(parent) {

  override fun validateTask(from: FROM): String {
    return from.toString()
  }
}