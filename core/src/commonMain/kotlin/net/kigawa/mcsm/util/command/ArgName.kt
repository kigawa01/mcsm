package net.kigawa.mcsm.util.command

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandArg(val name: String)
