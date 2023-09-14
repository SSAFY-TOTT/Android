package com.ssafy.tott.data.datasource.remote

import android.util.Log
import com.ssafy.tott.data.datasource.UserDataSource
import com.ssafy.tott.data.datasource.remote.service.UserService
import com.ssafy.tott.data.mapper.getErrorResponse
import com.ssafy.tott.data.model.request.LoginRequest
import com.ssafy.tott.data.model.request.SignupRequest
import com.ssafy.tott.data.model.request.VerificationRequest
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserDataSourceRemote @Inject constructor(private val userService: UserService) :
    UserDataSource {
    override fun makeCertNum(request: SignupRequest) = flow<Result<Unit>> {
        val response =
            userService.fetchSignup(request)
        if (response.isSuccessful) {
            Log.d("UserDataSourceRemote", "requestCert: $${response.body()}}")
            emit(Result.success(Unit))
        } else {
            val errorResponse = getErrorResponse(response.errorBody()?.string() ?: "")
            Log.d("UserDataSourceRemote", "requestCert: $errorResponse}")
            emit(Result.failure(errorResponse.toNetworkException()))
        }
    }.flowOn(Dispatchers.IO)

    override fun requestCert(accountNum: String, certNum: String) =
        flow<Result<Unit>> {
            val response =
                userService.fetchVerification(VerificationRequest(accountNum, certNum))
            if (response.isSuccessful) {
                Log.d("UserDataSourceRemote", "requestCert: ${response.body()}")
                emit(Result.success(Unit))
            } else {
                val errorResponse = getErrorResponse(response.errorBody()?.string() ?: "")
                Log.d("UserDataSourceRemote", "requestCert: $errorResponse}")
                emit(Result.failure(errorResponse.toNetworkException()))
            }
        }.flowOn(Dispatchers.IO)

    override fun login(id: String, password: String) = flow<Result<AuthTokenRemoteResponse>> {
        val response = userService.fetchLogin(LoginRequest(id, password))
        if (response.isSuccessful) {
            val jwtRemoteResponse = response.body() ?: return@flow emit(
                Result.failure(Throwable("데이터가 없습니다."))
            )
            emit(Result.success(jwtRemoteResponse))
        } else {
            val errorResponse = getErrorResponse(response.errorBody()?.string() ?: "")
            Log.d("UserDataSourceRemote", "login: $errorResponse}")
            emit(Result.failure(errorResponse.toNetworkException()))
        }
    }.flowOn(Dispatchers.IO)
}