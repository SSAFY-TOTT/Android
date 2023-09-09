package com.ssafy.tott.di

import com.ssafy.tott.data.model.response.BuildingListResponse
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchBuildingService {
    @FormUrlEncoded
    @GET("/api/auth/region/search")
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