package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.request.LoginRequest
import com.ssafy.tott.data.model.request.VerificationRequest
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/member/verification")
    suspend fun fetchVerification(
        @Body
        verificationRequest: VerificationRequest,
    ): Response<Unit>


    @POST("/api/auth/login")
    suspend fun fetchLogin(
        @Body
        loginRequest: LoginRequest,
    ): Response<AuthTokenRemoteResponse>
}