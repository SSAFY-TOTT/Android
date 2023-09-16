package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.repository.RegionRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDistrictListUseCase @Inject constructor(
    private val regionRepository: RegionRepository,
) {
    operator fun invoke() = flow<Result<Map<String, Int>>> {
        regionRepository.getDistrictPairList().collect { result ->
            result.onSuccess { districtList ->
                val map = districtList.associate { district -> district.name to district.code }
                emit(Result.success(map))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}