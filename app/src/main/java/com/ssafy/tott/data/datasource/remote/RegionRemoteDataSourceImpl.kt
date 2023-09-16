package com.ssafy.tott.data.datasource.remote

import android.util.Log
import com.ssafy.tott.data.datasource.remote.service.RegionService
import com.ssafy.tott.data.mapper.getErrorResponse
import com.ssafy.tott.data.model.response.RegionPairResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegionRemoteDataSourceImpl @Inject constructor(private val regionService: RegionService) :
    RegionRemoteDataSource {
    override fun getDistrictPairList() = flow<Result<List<RegionPairResponse>>> {
        val response = regionService.fetchGetDistrictList()
        if (response.isSuccessful) {
            Log.d("RegionRemoteDataSourceImpl", "getDistrictPairList: body ${response.body()}")
            val data = response.body()?.list ?: emptyList()
            Log.d("RegionRemoteDataSourceImpl", "getDistrictPairList: body ${data}")
            emit(Result.success(data))
        } else {
            val errorResponse = getErrorResponse(response.errorBody()?.string() ?: "")
            Log.d("BuildingDataSourceRemote", "login: $errorResponse}")
            emit(Result.failure(errorResponse.toNetworkException()))
        }
    }

    override fun getLegalDongPairList(districtCode: Int) = flow<Result<List<RegionPairResponse>>> {
        val response = regionService.fetchGetLegalDongList(districtCode)
        if (response.isSuccessful) {
            val data = response.body()?.list ?: emptyList()
            emit(Result.success(data))
        } else {
            val errorResponse = getErrorResponse(response.errorBody()?.string() ?: "")
            Log.d("BuildingDataSourceRemote", "login: $errorResponse}")
            emit(Result.failure(errorResponse.toNetworkException()))
        }
    }
}