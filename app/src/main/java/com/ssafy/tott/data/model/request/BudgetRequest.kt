package com.ssafy.tott.data.model.request


import com.google.gson.annotations.SerializedName

data class BudgetRequest(
    @SerializedName("annualIncome")
    val annualIncome: Int,
    @SerializedName("budgetSaveRequestList")
    val budgetSaveRequestList: List<BudgetSaveRequest>
)