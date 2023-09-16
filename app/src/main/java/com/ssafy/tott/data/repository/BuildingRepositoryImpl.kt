package com.ssafy.tott.data.repository

import com.ssafy.tott.data.datasource.remote.BuildingRemoteDataSource
import com.ssafy.tott.data.model.request.SearchFilterRequest.Companion.toData
import com.ssafy.tott.data.model.response.HouseDetailResponse
import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.HouseSaleArticle
import com.ssafy.tott.domain.model.SearchFilter
import com.ssafy.tott.domain.repository.BuildingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BuildingRepositoryImpl(private val buildingRemoteDataSource: BuildingRemoteDataSource) :
    BuildingRepository {
    override fun getBuildings(searchFilter: SearchFilter): Flow<Result<List<Building>>> {
        return buildingRemoteDataSource.searchBuilding(searchFilter.toData()).map { result ->
            result.map { response -> response.buildingListResponse.map { it.toDomain() } }
        }
    }

    override fun getRecentBuildingDetails(ids: List<Int>): Flow<Result<List<HouseSaleArticle>>> {
        return buildingRemoteDataSource.getRecentHouseArticles(ids).map { result ->
            result.map {
                it?.houseDetailResponseList?.map(HouseDetailResponse::toDomain) ?: emptyList()
            }
        }
    }

    override fun getWishList(): Flow<Result<List<HouseSaleArticle>>> {
        return buildingRemoteDataSource.getWishList().map { result ->
            result.map {
                it?.houseDetailResponseList?.map(HouseDetailResponse::toDomain) ?: emptyList()
            }
        }
    }
}