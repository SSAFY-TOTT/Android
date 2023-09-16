package com.ssafy.tott.data.model.response


import com.google.gson.annotations.SerializedName

data class ExtraMoneyResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("money")
    val money: Int
)