package com.ssafy.tott.data.model.response


import com.google.gson.annotations.SerializedName

data class BudgeResponse(
    @SerializedName("annualIncome")
    val annualIncome: Int,
    @SerializedName("budgets")
    val extraMoneyResponses: List<ExtraMoneyResponse>
)