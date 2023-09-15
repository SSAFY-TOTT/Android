package com.ssafy.tott.data.model.response

import com.ssafy.tott.domain.model.AuthToken

data class AuthTokenRemoteResponse(
    val accessToken: String,
    val accessTokenExpiresIn: Long,
    val grantType: String,
    val refreshToken: String,
) {
    fun toDomain() = AuthToken(
        accessToken = "${grantType.trim()} $accessToken",
        refreshToken = "${grantType.trim()} $refreshToken",
        accessTokenExpiresIn = accessTokenExpiresIn,
    )
}