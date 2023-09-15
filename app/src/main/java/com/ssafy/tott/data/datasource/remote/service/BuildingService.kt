package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.request.BuildingDetailIdListRequest
import com.ssafy.tott.data.model.response.BuildingDetailsResponse
import com.ssafy.tott.data.model.response.BuildingListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BuildingService {
    @GET("/api/house/search")
    suspend fun fetchSearchedBuilding(
        @Query("districtName") districtName: String,
        @Query("legalDongName") legalDongName: String,
        @Query("minPrice") minPrice: Int,
        @Query("maxPrice") maxPrice: Int,
        @Query("minArea") minArea: Int,
        @Query("maxArea") maxArea: Int,
        @Query("types") types: List<String>,
        @Query("built") built: Int,
    ): Response<BuildingListResponse>

    @POST
    suspend fun fetchGetRecentBuildings(
        @Body buildingDetailIdListRequest: BuildingDetailIdListRequest
    ): Response<BuildingDetailsResponse>
}