package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.request.BudgetRequest
import com.ssafy.tott.data.model.request.LoginRequest
import com.ssafy.tott.data.model.request.SignupRequest
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

    @POST("/api/member/signup")
    suspend fun fetchSignup(
        @Body
        signupRequest: SignupRequest,
    ): Response<Unit>

    @POST("/api/auth/refresh")
    suspend fun refreshToken(
        @Body
        refreshToken: String,
        // TODO request 내용 작성 필요
    ): Response<AuthTokenRemoteResponse>

    @POST("/api/budget/auth/save")
    suspend fun fetchSaveBudgetMoney(
        @Body
        budgetRequest: BudgetRequest
    ): Response<Unit>
}