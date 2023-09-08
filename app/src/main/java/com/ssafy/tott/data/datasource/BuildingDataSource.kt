package com.ssafy.tott.data.datasource

import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.SearchFilter
import kotlinx.coroutines.flow.Flow

interface BuildingDataSource {
    fun searchBuilding(searchFilter: SearchFilter): Flow<List<Building>>
}