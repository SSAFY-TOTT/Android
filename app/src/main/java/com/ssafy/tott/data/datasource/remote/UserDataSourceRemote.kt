package com.ssafy.tott.data.datasource.remote

import android.util.Log
import com.ssafy.tott.data.datasource.UserDataSource
import com.ssafy.tott.data.datasource.mapper.getErrorResponse
import com.ssafy.tott.data.datasource.remote.service.UserService
import com.ssafy.tott.data.model.RegisterAccountRequest
import com.ssafy.tott.data.model.request.LoginRequest
import com.ssafy.tott.data.model.request.VerificationRequest
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserDataSourceRemote @Inject constructor(private val userService: UserService) :
    UserDataSource {
    override fun makeCertNum(request: RegisterAccountRequest): Result<Unit> {
//        return Result.failure(ConnectException("연결에 실패했습니다."))
        return Result.success(Unit)
    }

    override fun requestCert(id: String, password: String, certNum: Int) =
        flow<Result<Unit>> {
            val response =
                userService.fetchVerification(VerificationRequest(id, certNum = certNum.toString()))
            if (response.isSuccessful) {
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