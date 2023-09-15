package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.model.HouseSaleArticle
import com.ssafy.tott.domain.repository.BuildingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentHouseArticlesUseCase @Inject constructor(
    private val buildingRepository: BuildingRepository
) {
    operator fun invoke(ids: List<Int>): Flow<Result<List<HouseSaleArticle>>> {
        return buildingRepository.getRecentBuildingDetails(ids)
    }
}