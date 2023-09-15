package com.ssafy.tott.domain.repository

import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.HouseSaleArticle
import com.ssafy.tott.domain.model.SearchFilter
import kotlinx.coroutines.flow.Flow


interface BuildingRepository {
    fun getBuildings(searchFilter: SearchFilter): Flow<Result<List<Building>>>
    fun getRecentBuildingDetails(ids: List<Int>): Flow<Result<List<HouseSaleArticle>>>
}