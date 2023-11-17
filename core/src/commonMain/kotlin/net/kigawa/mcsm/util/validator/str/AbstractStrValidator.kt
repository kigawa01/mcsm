package net.kigawa.mcsm.util.validator.str

import net.kigawa.mcsm.util.validator.AbstractValidator
import net.kigawa.mcsm.util.validator.Validator

abstract class AbstractStrValidator<ORIGINAL : Any?, FROM : Any?>(parent: Validator<ORIGINAL, *, FROM>) :
  AbstractValidator<ORIGINAL, FROM, String>(parent) {
}