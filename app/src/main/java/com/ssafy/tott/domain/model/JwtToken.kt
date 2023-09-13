package com.ssafy.tott.domain.model

data class JwtToken(
    val accessToken:String,
    val refreshToken:String,
    val accessTokenExpiresIn: Long,
)
