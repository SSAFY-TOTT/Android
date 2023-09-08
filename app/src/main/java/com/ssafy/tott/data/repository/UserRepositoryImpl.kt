package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.AccountDataSource
import com.ssafy.tott.data.datasource.mapper.toData
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val accountDataSource: AccountDataSource) :
    UserRepository {
    override fun makeCertNum(registerUser: RegisterUser): Result<Unit> {
        return accountDataSource.makeCertNum(registerUser.toData())
    }
}