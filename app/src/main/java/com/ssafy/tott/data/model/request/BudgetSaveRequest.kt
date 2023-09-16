package com.ssafy.tott.data.model.request


import com.google.gson.annotations.SerializedName

data class BudgetSaveRequest(
    @SerializedName("message")
    val message: String,
    @SerializedName("money")
    val money: Int
)