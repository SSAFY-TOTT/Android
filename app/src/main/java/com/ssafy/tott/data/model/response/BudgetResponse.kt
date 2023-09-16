package com.ssafy.tott.data.model.response


import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.ExtraMoney

data class BudgetResponse(
    @SerializedName("annualIncome") val annualIncome: Int,
    @SerializedName("budgets") val extraMoneyResponses: List<ExtraMoneyResponse>
) {
    fun toDomain(): List<ExtraMoney> {
        val list = mutableListOf<ExtraMoney>()
        list.add(ExtraMoney("연소득", annualIncome))
        list.addAll(extraMoneyResponses.map { ExtraMoney(it.message, it.money) })
        return list
    }
}