package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.request.BudgetRequest
import com.ssafy.tott.data.model.response.BudgetResponse
import com.ssafy.tott.data.model.response.ComprehensiveBudgetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAuthService {
    @POST("/api/budget/auth/save")
    suspend fun fetchSaveBudgetMoney(
        @Body
        budgetRequest: BudgetRequest
    ): Response<Unit>

    @GET("/api/budget/auth/find")
    suspend fun fetchGetBudgetMoney(
    ): Response<BudgetResponse>

    @GET("/api/house/auth/state")
    suspend fun fetchGetComprehensiveBudget(
        @Query("price")
        price: Int,
    ): Response<ComprehensiveBudgetResponse>
}