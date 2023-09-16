package com.ssafy.tott.domain.repository

import com.ssafy.tott.domain.model.AuthToken
import com.ssafy.tott.domain.model.ExtraMoney
import com.ssafy.tott.domain.model.RegisterUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun makeCertNum(registerUser: RegisterUser): Flow<Result<Unit>>
    fun login(id: String, password: String): Flow<Result<AuthToken>>
    fun saveToken(authToken: AuthToken): Flow<Unit>
    fun getAccessToken(): Flow<Result<String>>
    fun getRefreshToken(): Flow<Result<String>>
    fun requestCert(accountNum: String, certNum: String): Flow<Result<Unit>>
    fun saveBudget(extraMoneyList: List<ExtraMoney>): Flow<Result<Unit>>
    fun getBudget(): Flow<Result<List<ExtraMoney>>>
}