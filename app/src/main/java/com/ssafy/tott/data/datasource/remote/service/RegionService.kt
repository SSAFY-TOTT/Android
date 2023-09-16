package com.ssafy.tott.data.datasource.remote.service

import com.ssafy.tott.data.model.response.DistrictListResponse
import com.ssafy.tott.data.model.response.LegalDongListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RegionService {
    @GET("/api/region/auth/find/district")
    suspend fun fetchGetDistrictList(): Response<DistrictListResponse>

    @GET("/api/region/auth/find/legal")
    suspend fun fetchGetLegalDongList(
        @Query("districtCode")
        code: Int
    ): Response<LegalDongListResponse>
}