package com.ssafy.tott.data.model

data class RegisterAccountRequest(
    val id: String,
    val password: String,
    val validPassword: String,
    val phone: String,
    val accountNum: String,
)
