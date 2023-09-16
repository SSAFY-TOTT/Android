package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.local.UserTokenDataSource
import com.ssafy.tott.data.datasource.remote.UserRemoteDataSource
import com.ssafy.tott.data.model.request.BudgetRequest.Companion.toData
import com.ssafy.tott.data.model.request.SignupRequest.Companion.toData
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import com.ssafy.tott.domain.model.AuthToken
import com.ssafy.tott.domain.model.ExtraMoney
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userTokenDataSource: UserTokenDataSource
) : UserRepository {
    override fun makeCertNum(registerUser: RegisterUser): Flow<Result<Unit>> {
        return userRemoteDataSource.makeCertNum(registerUser.toData())
    }

    override fun requestCert(accountNum: String, certNum: String): Flow<Result<Unit>> {
        return userRemoteDataSource.requestCert(accountNum, certNum)
    }

    override fun login(id: String, password: String): Flow<Result<AuthToken>> {
        return userRemoteDataSource.login(id, password).map { result ->
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

    override fun saveBudget(extraMoneyList: List<ExtraMoney>): Flow<Result<Unit>> {
        return userRemoteDataSource.saveBudget(extraMoneyList.toData())
    }
}