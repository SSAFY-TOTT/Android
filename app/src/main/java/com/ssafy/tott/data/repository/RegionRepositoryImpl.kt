package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.remote.RegionRemoteDataSource
import com.ssafy.tott.domain.model.RegionPair
import com.ssafy.tott.domain.repository.RegionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RegionRepositoryImpl @Inject constructor(
    private val regionRemoteDataSource: RegionRemoteDataSource,
) : RegionRepository {
    override fun getDistrictPairList(): Flow<Result<List<RegionPair>>> {
        return regionRemoteDataSource.getDistrictPairList().map { result ->
            result.map { list -> list.map { it.toDomain() } }
        }
    }

    override fun getLegalDongPairList(districtCode: Int): Flow<Result<List<RegionPair>>> {
        return regionRemoteDataSource.getLegalDongPairList(districtCode).map { result ->
            result.map { list -> list.map { it.toDomain() } }
        }
    }
}