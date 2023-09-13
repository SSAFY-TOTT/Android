package com.ssafy.tott.data.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserTokenDataSourceImpl @Inject constructor(private val dataStoreManager: DataStoreManager) :
    UserTokenDataSource {
    override fun getAccessToken(): Flow<Result<String>> {
        return dataStoreManager.accessToken.map {
            if (it == null) {
                Result.failure(Throwable("토큰이 없습니다."))
            } else {
                Result.success(it)
            }
        }
    }

    override fun getRefreshToken(): Flow<Result<String>> {
        return dataStoreManager.refreshToken.map {
            if (it == null) {
                Result.failure(Throwable("토큰이 없습니다."))
            } else {
                Result.success(it)
            }
        }
    }

    override fun setToken(accessToken: String, refreshToken: String): Flow<Unit> = flow {
        dataStoreManager.setRefreshToken(refreshToken)
        dataStoreManager.setAccessToken(accessToken)
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}