package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.SearchFilter
import com.ssafy.tott.domain.repository.BuildingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBuildingUseCase @Inject constructor(private val buildingRepository: BuildingRepository) {
    operator fun invoke(searchFilter: SearchFilter): Flow<Result<List<Building>>> {
        return buildingRepository.getBuildings(searchFilter)
    }
}