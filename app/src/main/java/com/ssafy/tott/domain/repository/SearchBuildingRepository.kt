package com.ssafy.tott.domain.repository

import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.SearchFilter
import kotlinx.coroutines.flow.Flow


interface SearchBuildingRepository {
    fun getBuildings(searchFilter: SearchFilter): Flow<List<Building>>
}