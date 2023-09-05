package com.ssafy.tott.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class SearchMapViewModel : ViewModel() {
    val districtName = MutableLiveData<String>("강남구")
    val legalDongName = MutableLiveData<String>("역삼1동")
    private val minPrice = MutableLiveData<Int>(13)
    private val maxPrice = MutableLiveData<Int>(17)
    private val minArea = MutableLiveData<Int>(13)
    private val maxArea = MutableLiveData<Int>(21)
    val type = MutableLiveData<List<String>>(listOf("아파트"))
    val built = MutableLiveData<String>("전체")

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
}