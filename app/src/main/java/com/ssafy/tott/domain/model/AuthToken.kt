package com.ssafy.tott.domain.model

data class AuthToken(
    val accessToken:String,
    val refreshToken:String,
    val accessTokenExpiresIn: Long,
)
