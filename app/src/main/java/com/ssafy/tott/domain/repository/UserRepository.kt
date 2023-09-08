package com.ssafy.tott.domain.repository

import com.ssafy.tott.domain.model.RegisterUser

interface UserRepository {
    fun makeCertNum(registerUser: RegisterUser): Result<Unit>
}