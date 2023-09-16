package com.ssafy.tott.data.model.request


import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.ExtraMoney

data class BudgetRequest(
    @SerializedName("annualIncome")
    val annualIncome: Int,
    @SerializedName("budgetSaveRequestList")
    val budgetSaveRequestList: List<BudgetSaveRequest>
) {
    companion object {
        fun List<ExtraMoney>.toData() = BudgetRequest(
            this[0].money,
            drop(1).map {
                BudgetSaveRequest(it.name, it.money)
            }
        )
    }
}