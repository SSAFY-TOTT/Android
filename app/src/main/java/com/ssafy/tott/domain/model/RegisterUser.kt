package com.ssafy.tott.domain.model

data class RegisterUser(
    val id: String,
    val password: String,
    val password2: String,
    val phone: String,
    val accountNum: String
)
