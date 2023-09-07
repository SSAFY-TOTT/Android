package com.ssafy.tott.data.datasource.remote

import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.BuildingDetail
import com.ssafy.tott.domain.model.SearchFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BuildingRemoteDataSource : BuildingDataSource {
    override fun searchBuilding(searchFilter: SearchFilter): Flow<List<Building>> = flow {
        // TODO: 임시 데이터
        val list = listOf<Building>(
            Building(37.5152464, 127.0428016, listOf(BuildingDetail(234, 122, 18, 2)), "동", "구", "건물"),
            Building(37.5052464, 127.0498016, listOf(BuildingDetail(0, 1, 3, null)), "동", "구", "건물")
        )
        emit(list)
    }
}