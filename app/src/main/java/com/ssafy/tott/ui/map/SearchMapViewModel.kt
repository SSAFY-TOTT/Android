package com.ssafy.tott.ui.map

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.SearchFilter
import com.ssafy.tott.domain.usecase.SearchBuildingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMapViewModel @Inject constructor(val useCase: SearchBuildingUseCase) :
    ViewModel() {
    private val _buildings = MutableStateFlow<List<Building>>(listOf())
    val buildings = _buildings.asStateFlow()

    private val districtName = MutableLiveData<String>(null)
    private val legalDongName = MutableLiveData<String>(null)
    private val minPrice = MutableLiveData<Int>(MIN_PRICE)
    private val maxPrice = MutableLiveData<Int>(MAX_PRICE)
    private val minArea = MutableLiveData<Int>(MIN_AREA)
    private val maxArea = MutableLiveData<Int>(MAX_AREA)
    private val type = MutableLiveData<List<String>>(BUILDING_TYPES)
    private val built = MutableLiveData<Int>(DEFAULT_BUILT)

    fun getDistrictName() = districtName.value

    fun getLegalDongName() = legalDongName.value

    var priceList: List<Float> = listOf(MIN_PRICE, MAX_PRICE).map(Int::toFloat)
        get() = listOf(
            minPrice.value ?: MIN_PRICE,
            maxPrice.value ?: MAX_PRICE
        ).map(Int::toFloat)
        set(value) {
            minPrice.value = value[0].toInt()
            maxPrice.value = value[1].toInt()
            field = value
        }
    var areaList = listOf(MIN_AREA, MAX_AREA).map(Int::toFloat)
        get() = listOf(
            minArea.value ?: MIN_AREA,
            maxArea.value ?: MAX_AREA
        ).map(Int::toFloat)
        set(value) {
            minArea.value = value[0].toInt()
            maxArea.value = value[1].toInt()
            field = value
        }

    fun saveFilterSetting(
        districtName: String, legalDongName: String, built: Int,
        priceList: List<Float>, areaList: List<Float>, types: List<String>,
    ) {
        Log.d(
            this::class.java.simpleName, "loadData: $districtName $legalDongName $built" +
                    "$priceList $areaList $types"
        )
        this.districtName.value = districtName
        this.legalDongName.value = legalDongName
        this.built.value = built
        this.priceList = priceList
        this.areaList = areaList
        this.type.value = types
        loadFilteredData()
    }

    private fun loadFilteredData() {
        val districtName = districtName.value ?: return
        val legalDongName = legalDongName.value ?: return
        val built = built.value ?: DEFAULT_BUILT
        val minArea = minArea.value ?: MIN_AREA
        val maxArea = maxArea.value ?: MAX_AREA
        val minPrice = minPrice.value ?: MIN_AREA
        val maxPrice = maxPrice.value ?: MAX_PRICE
        val type = type.value ?: BUILDING_TYPES
        val searchFilter = SearchFilter(
            districtName, legalDongName, minPrice,
            maxPrice, minArea, maxArea,
            type, built
        )
        viewModelScope.launch {
            useCase(searchFilter).collect { result ->
                Log.d(this::class.simpleName, "loadFilteredData: $result")
                result.onSuccess {
                    _buildings.value = result.getOrThrow()
                }.onFailure {
                    Log.d(this::class.simpleName, "loadFilteredData: ${it.message}")
                }
            }
        }
    }

    companion object {
        const val MIN_PRICE = 0
        const val MAX_PRICE = 100
        const val MIN_AREA = 3
        const val MAX_AREA = 60
        const val DEFAULT_BUILT = 1000
        var BUILDING_TYPES = listOf<String>()
    }
}