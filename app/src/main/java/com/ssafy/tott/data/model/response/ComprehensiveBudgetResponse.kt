package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.ComprehensiveBudget

data class ComprehensiveBudgetResponse(
    @SerializedName("amount")
    val accountMoney: Int,
    @SerializedName("budget")
    val extraMoney: Int,
    @SerializedName("creditLine")
    val creditLine: Int,
) {
    fun toDomain() = ComprehensiveBudget(
        accountMoney = accountMoney, extraMoney = extraMoney, creditLine = creditLine,
    )
}