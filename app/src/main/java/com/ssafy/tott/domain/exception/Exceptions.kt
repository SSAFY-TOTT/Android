package com.ssafy.tott.domain.exception

class ConnectException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)