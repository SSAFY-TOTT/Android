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

    val priceList: List<Float>
        get() = listOf(minPrice.value?.toFloat() ?: 0f, maxPrice.value?.toFloat() ?: 200f)
    val areaList
        get() = listOf(minArea.value?.toFloat() ?: 0f, maxArea.value?.toFloat() ?: 100f)
}