package com.ssafy.tott.data.datasource

import com.ssafy.tott.data.model.RegisterAccountRequest

interface AccountDataSource {
    fun makeCertNum(request: RegisterAccountRequest): Result<Unit>
    fun requestCert(id: String, password: String, certNum: Int): Result<Nothing>
}