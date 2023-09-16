package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.request.BudgetRequest
import com.ssafy.tott.data.model.response.BudgetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAuthService {
    @POST("/api/budget/auth/save")
    suspend fun fetchSaveBudgetMoney(
        @Body
        budgetRequest: BudgetRequest
    ): Response<Unit>

    @GET("/api/budget/auth/find")
    suspend fun fetchGetBudgetMoney(
    ): Response<BudgetResponse>
}