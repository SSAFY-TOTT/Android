package com.ssafy.tott.domain.repository

import com.ssafy.tott.domain.model.RegionPair
import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun getDistrictPairList(): Flow<Result<List<RegionPair>>>
    fun getLegalDongPairList(districtCode: Int): Flow<Result<List<RegionPair>>>
}