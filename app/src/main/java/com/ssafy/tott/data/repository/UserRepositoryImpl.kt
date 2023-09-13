package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.UserDataSource
import com.ssafy.tott.data.datasource.mapper.toData
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import com.ssafy.tott.domain.model.AuthToken
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override fun makeCertNum(registerUser: RegisterUser): Result<Unit> {
        return userDataSource.makeCertNum(registerUser.toData())
    }

    override fun login(id: String, password: String): Flow<Result<AuthToken>> {
        return userDataSource.login(id, password).map { result ->
            result.map(AuthTokenRemoteResponse::toDomain)
        }
    }

    override fun refreshToken(token: String): Flow<Result<AuthToken>> {
        TODO("Not yet implemented")
    }

    override fun saveToken(authToken: AuthToken): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getToken(): Flow<Result<String>> {
        TODO("Not yet implemented")
    }
}