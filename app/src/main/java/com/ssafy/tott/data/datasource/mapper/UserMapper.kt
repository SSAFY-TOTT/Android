package com.ssafy.tott.data.datasource.mapper

import com.ssafy.tott.data.model.RegisterAccountRequest
import com.ssafy.tott.domain.model.RegisterUser

fun RegisterUser.toData() =
    RegisterAccountRequest(id, password, password2, phone, accountNum)