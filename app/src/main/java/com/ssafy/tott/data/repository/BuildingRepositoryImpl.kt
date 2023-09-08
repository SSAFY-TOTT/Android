package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.SearchFilter
import com.ssafy.tott.domain.repository.SearchBuildingRepository
import kotlinx.coroutines.flow.Flow

class BuildingRepositoryImpl(private val buildingDataSource: BuildingDataSource) :
    SearchBuildingRepository {
    override fun getBuildings(searchFilter: SearchFilter): Flow<List<Building>> {
        return buildingDataSource.searchBuilding(searchFilter)
    }
}