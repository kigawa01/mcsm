package net.kigawa.mcsm.util.validator

abstract class AbstractValidator<ORIGINAL : Any?, FROM : Any?, TO : Any?>(
  private val parent: Validator<ORIGINAL, *, FROM>
) : Validator<ORIGINAL, FROM, TO> {
  override fun validate(value: ORIGINAL): TO {
    val from = parent.validate(value)
    return validateTask(from)
  }

  abstract fun validateTask(from: FROM): TO
}