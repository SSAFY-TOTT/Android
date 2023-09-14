package com.ssafy.tott.data.model.request

import com.google.gson.annotations.SerializedName

data class VerificationRequest(
    @SerializedName("accountNumber")
    val account: String,
    @SerializedName("memo")
    val certNum: String,
)
