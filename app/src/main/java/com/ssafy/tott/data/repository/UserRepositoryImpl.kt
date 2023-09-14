package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.UserDataSource
import com.ssafy.tott.data.datasource.local.UserTokenDataSource
import com.ssafy.tott.data.model.request.SignupRequest.Companion.toData
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import com.ssafy.tott.domain.model.AuthToken
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userTokenDataSource: UserTokenDataSource
) : UserRepository {
    override fun makeCertNum(registerUser: RegisterUser): Flow<Result<Unit>> {
        return userDataSource.makeCertNum(registerUser.toData())
    }

    override fun requestCert(accountNum: String, certNum: String): Flow<Result<Unit>> {
        return userDataSource.requestCert(accountNum, certNum)
    }

    override fun login(id: String, password: String): Flow<Result<AuthToken>> {
        return userDataSource.login(id, password).map { result ->
            result.map(AuthTokenRemoteResponse::toDomain)
        }
    }

    override fun saveToken(authToken: AuthToken): Flow<Unit> {
        return userTokenDataSource.setToken(authToken.accessToken, authToken.refreshToken)
    }

    override fun getAccessToken(): Flow<Result<String>> {
        return userTokenDataSource.getAccessToken()
    }

    override fun getRefreshToken(): Flow<Result<String>> {
        return userTokenDataSource.getRefreshToken()
    }
}