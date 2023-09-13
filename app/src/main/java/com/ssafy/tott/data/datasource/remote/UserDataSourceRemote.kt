package com.ssafy.tott.data.datasource.remote

import android.util.Log
import com.ssafy.tott.data.datasource.UserDataSource
import com.ssafy.tott.data.model.RegisterAccountRequest
import com.ssafy.tott.data.model.response.JWTRemoteResponse
import com.ssafy.tott.di.LoginService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataSourceRemote @Inject constructor(private val loginService: LoginService) :
    UserDataSource {
    override fun makeCertNum(request: RegisterAccountRequest): Result<Unit> {
//        return Result.failure(ConnectException("연결에 실패했습니다."))
        return Result.success(Unit)
    }

    override fun requestCert(id: String, password: String, certNum: Int): Result<Nothing> {
        TODO("Not yet implemented")
    }

    override fun login(id: String, password: String) = flow<Result<JWTRemoteResponse>> {
        val response = loginService.fetchLogin(id, password)
        if (response.isSuccessful) {
            val jwtRemoteResponse = response.body() ?: return@flow emit(
                Result.failure(Throwable("데이터가 없습니다."))
            )
            emit(Result.success(jwtRemoteResponse))
        } else {
            Log.d("UserDataSourceRemote", "login: ${response.errorBody()?.string()}")
            emit(Result.failure(Throwable(response.errorBody()?.string())))
        }
    }
}