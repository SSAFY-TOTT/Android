package com.ssafy.tott.ui.map

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMapViewModel @Inject() constructor() : ViewModel() {
    private val districtName = MutableLiveData<String>("강남구")
    private val legalDongName = MutableLiveData<String>("역삼1동")
    private val minPrice = MutableLiveData<Int>(13)
    private val maxPrice = MutableLiveData<Int>(17)
    private val minArea = MutableLiveData<Int>(13)
    private val maxArea = MutableLiveData<Int>(21)
    private val type = MutableLiveData<List<String>>(listOf("아파트"))
    private val built = MutableLiveData<String>("전체")

    fun getDistrictName() = districtName.value

    fun getLegalDongName() = legalDongName.value

    var priceList: List<Float> = listOf(0f, 0f)
        get() = listOf(minPrice.value?.toFloat() ?: 0f, maxPrice.value?.toFloat() ?: 200f)
        set(value) {
            minPrice.value = value[0].toInt()
            maxPrice.value = value[1].toInt()
            field = value
        }
    var areaList = listOf(0f, 0f)
        get() = listOf(minArea.value?.toFloat() ?: 0f, maxArea.value?.toFloat() ?: 100f)
        set(value) {
            minArea.value = value[0].toInt()
            maxArea.value = value[1].toInt()
            field = value
        }

    fun saveFilterSetting(
        districtName: String, legalDongName: String, built: String,
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
        // TODO 필터 설정 데이터 전송 필요
    }
}