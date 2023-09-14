package com.ssafy.tott.data.datasource

import com.ssafy.tott.data.model.RegisterAccountRequest
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun makeCertNum(request: RegisterAccountRequest): Result<Unit>
    fun requestCert(email: String, certNum: String): Flow<Result<Unit>>
    fun login(id: String, password: String): Flow<Result<AuthTokenRemoteResponse>>
}