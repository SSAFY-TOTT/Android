package com.ssafy.tott.data.datasource

import com.ssafy.tott.data.model.request.SearchFilterRequest
import com.ssafy.tott.data.model.response.BuildingDetailsResponse
import com.ssafy.tott.data.model.response.BuildingListResponse
import kotlinx.coroutines.flow.Flow

interface BuildingDataSource {
    fun searchBuilding(searchFilter: SearchFilterRequest): Flow<Result<BuildingListResponse>>
    fun getRecentHouseArticles(ids: List<Int>): Flow<Result<BuildingDetailsResponse?>>
    fun getWishList(): Flow<Result<BuildingDetailsResponse?>>
}