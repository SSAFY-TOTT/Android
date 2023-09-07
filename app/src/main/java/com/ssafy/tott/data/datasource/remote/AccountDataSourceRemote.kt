package com.ssafy.tott.data.datasource.remote

import com.ssafy.tott.data.datasource.AccountDataSource
import com.ssafy.tott.data.model.RegisterAccountRequest
import javax.inject.Inject

class AccountDataSourceRemote @Inject constructor() : AccountDataSource {
    override fun makeCertNum(request: RegisterAccountRequest): Result<Unit> {
//        return Result.failure(ConnectException("연결에 실패했습니다."))
        return Result.success(Unit)
    }

    override fun requestCert(id: String, password: String, certNum: Int): Result<Nothing> {
        TODO("Not yet implemented")
    }
}