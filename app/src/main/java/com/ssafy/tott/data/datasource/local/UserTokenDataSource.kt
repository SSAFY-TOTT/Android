package com.ssafy.tott.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface UserTokenDataSource {
    fun getAccessToken(): Flow<Result<String>>
    fun getRefreshToken(): Flow<Result<String>>
    fun setToken(accessToken: String, refreshToken: String): Flow<Unit>
}