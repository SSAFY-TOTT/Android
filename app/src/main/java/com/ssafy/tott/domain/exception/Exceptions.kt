package com.ssafy.tott.domain.exception

class NetworkException(
    val code: Int?,
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)

class ExpiredTokenException(
    val code: Int?,
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)