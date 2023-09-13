package com.ssafy.tott.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val id: String,
    @SerializedName("password")
    val password: String,
)
