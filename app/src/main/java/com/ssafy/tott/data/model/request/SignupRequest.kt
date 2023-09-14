package com.ssafy.tott.data.model.request


import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.RegisterUser

data class SignupRequest(
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("bankCode")
    val bankCode: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("validPassword")
    val validPassword: String
) {
    companion object {
        fun RegisterUser.toData() = SignupRequest(
            accountNumber = accountNum,
            bankCode = "SHINHAN",
            email = id,
            password = password,
            phoneNumber = phone,
            validPassword = validPassword
        )
    }
}