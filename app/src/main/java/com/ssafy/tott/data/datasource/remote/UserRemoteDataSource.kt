package com.ssafy.tott.data.datasource.remote

import com.ssafy.tott.data.model.request.SignupRequest
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    fun makeCertNum(request: SignupRequest): Flow<Result<Unit>>
    fun requestCert(accountNum: String, certNum: String): Flow<Result<Unit>>
    fun login(id: String, password: String): Flow<Result<AuthTokenRemoteResponse>>
}