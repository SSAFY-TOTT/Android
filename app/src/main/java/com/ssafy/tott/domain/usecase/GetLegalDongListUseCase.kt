package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.repository.RegionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLegalDongListUseCase @Inject constructor(private val regionRepository: RegionRepository) {
    operator fun invoke(districtCode: Int): Flow<Result<Map<String, Int>>> = flow {
        regionRepository.getLegalDongPairList(districtCode).collect { dongResult ->
            dongResult.onSuccess { dongList ->
                val map = dongList.associate { dong -> dong.name to dong.code }
                emit(Result.success(map))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}