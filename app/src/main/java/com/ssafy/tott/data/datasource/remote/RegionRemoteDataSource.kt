package com.ssafy.tott.data.datasource.remote

import com.ssafy.tott.data.model.response.RegionPairResponse
import kotlinx.coroutines.flow.Flow

interface RegionRemoteDataSource {
    fun getDistrictPairList(): Flow<Result<List<RegionPairResponse>>>
    fun getLegalDongPairList(districtCode: Int): Flow<Result<List<RegionPairResponse>>>
}