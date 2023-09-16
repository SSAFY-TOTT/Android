package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.response.RegionPairResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RegionService {
    @POST("/api/region/auth/find/district")
    suspend fun fetchGetDistrictList(): Response<Map<String, List<RegionPairResponse>>>

    @GET("/api/region/auth/find/legal")
    suspend fun fetchGetLegalDongList(
        @Query("districtCode")
        code: Int
    ): Response<Map<String, List<RegionPairResponse>>>
}