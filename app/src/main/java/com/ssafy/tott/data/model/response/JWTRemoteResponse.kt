package com.ssafy.tott.data.model.response

data class JWTRemoteResponse(
    val accessToken: String,
    val accessTokenExpiresIn: Long,
    val grantType: String,
    val refreshToken: String
)