package com.howalog.exception

abstract class HowalogException(
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException() {

    abstract fun getStatusCode(): Int
}