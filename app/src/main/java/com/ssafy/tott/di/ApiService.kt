package com.ssafy.tott.di

import com.ssafy.tott.data.model.request.LoginRequest
import com.ssafy.tott.data.model.response.AuthTokenRemoteResponse
import com.ssafy.tott.data.model.response.BuildingListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchBuildingService {
    @GET("/api/house/search")
    suspend fun fetchSearchedBuilding(
        @Query("districtName")
        districtName: String,
        @Query("legalDongName")
        legalDongName: String,
        @Query("minPrice")
        minPrice: Int,
        @Query("maxPrice")
        maxPrice: Int,
        @Query("minArea")
        minArea: Int,
        @Query("maxArea")
        maxArea: Int,
        @Query("types")
        types: List<String>,
        @Query("built")
        built: Int,
    ): Response<BuildingListResponse>
}

interface LoginService {
    @POST("/api/auth/login")
    suspend fun fetchLogin(
        @Body
        loginRequest: LoginRequest,
    ): Response<AuthTokenRemoteResponse>
}