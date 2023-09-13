package com.ssafy.tott.domain.repository

import com.ssafy.tott.domain.model.AuthToken
import com.ssafy.tott.domain.model.RegisterUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun makeCertNum(registerUser: RegisterUser): Result<Unit>
    fun login(id: String, password: String): Flow<Result<AuthToken>>
    fun refreshToken(token: String): Flow<Result<AuthToken>>
    fun saveToken(authToken: AuthToken): Flow<Result<Unit>>
    fun getToken(): Flow<Result<String>>
}