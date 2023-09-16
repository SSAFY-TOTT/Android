package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.request.BuildingDetailIdListRequest
import com.ssafy.tott.data.model.request.SearchFilterRequest
import com.ssafy.tott.data.model.response.BuildingDetailsResponse
import com.ssafy.tott.data.model.response.BuildingListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BuildingService {
    @POST("/api/geo/auth/search")
    suspend fun fetchSearchedBuilding(
        @Body buildingSearchRequest: SearchFilterRequest
    ): Response<BuildingListResponse>

    @POST("/api/house/auth/recent")
    suspend fun fetchGetRecentBuildings(
        @Body buildingDetailIdListRequest: BuildingDetailIdListRequest
    ): Response<BuildingDetailsResponse>

    @POST("/api/wishlist/auth/view")
    suspend fun fetchGetWishListBuildings(
    ): Response<BuildingDetailsResponse>
}