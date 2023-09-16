package com.ssafy.tott.data.datasource.remote

import android.util.Log
import com.ssafy.tott.data.datasource.BuildingDataSource
import com.ssafy.tott.data.datasource.remote.service.BuildingService
import com.ssafy.tott.data.mapper.getErrorResponse
import com.ssafy.tott.data.model.request.BuildingDetailIdListRequest
import com.ssafy.tott.data.model.request.SearchFilterRequest
import com.ssafy.tott.data.model.response.BuildingDetailsResponse
import com.ssafy.tott.data.model.response.BuildingListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BuildingRemoteDataSource @Inject constructor(private val buildingService: BuildingService) :
    BuildingDataSource {
    override fun searchBuilding(searchFilter: SearchFilterRequest): Flow<Result<BuildingListResponse>> =
        flow {
            val response = buildingService.fetchSearchedBuilding(searchFilter)
            if (response.isSuccessful) {
                val data = response.body() ?: BuildingListResponse(listOf(), "")
                Log.d("BuildingRemoteDataSource", "searchBuilding suspendOnSuccess: $data")
                emit(Result.success(data))
            } else {
                val e = response.errorBody()?.string() ?: "error"
                Log.d("BuildingDataSourceRemote", "login: $e}")
                val errorResponse = getErrorResponse(e)
                Log.d("BuildingDataSourceRemote", "login: $errorResponse}")
                emit(Result.failure(errorResponse.toNetworkException()))
            }
        }.flowOn(Dispatchers.IO)

    override fun getRecentHouseArticles(ids: List<Int>) = flow<Result<BuildingDetailsResponse?>> {
        val response = buildingService.fetchGetRecentBuildings(BuildingDetailIdListRequest(ids))
        if (response.isSuccessful) {
            emit(Result.success(response.body()))
        } else {
            val errorResponse = getErrorResponse(response.errorBody()?.string() ?: "")
            Log.d("BuildingDataSourceRemote", "login: $errorResponse}")
            emit(Result.failure(errorResponse.toNetworkException()))
        }
    }
}