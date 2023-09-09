package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.data.model.request.SearchFilterRequest.Companion.toData
import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.SearchFilter
import com.ssafy.tott.domain.repository.SearchBuildingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BuildingRepositoryImpl(private val buildingDataSource: BuildingDataSource) :
    SearchBuildingRepository {
    override fun getBuildings(searchFilter: SearchFilter): Flow<Result<List<Building>>> {
        return buildingDataSource.searchBuilding(searchFilter.toData()).map { result ->
            result.map { response -> response.buildingListResponse.map { it.toDomain() } }
        }
    }
}