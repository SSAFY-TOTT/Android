package com.ssafy.tott.ui.home

import androidx.lifecycle.ViewModel
import com.ssafy.tott.domain.model.HouseSaleArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _recentBuildingDetails = MutableStateFlow<List<HouseSaleArticle>>(listOf())
    val recentBuildingDetails = _recentBuildingDetails.asStateFlow()

    private val _favoriteBuildingDetails = MutableStateFlow<List<HouseSaleArticle>>(listOf())
    val favoriteBuildingDetails = _recentBuildingDetails.asStateFlow()

    init {
        loadRecentBuildingDetails()
        loadFavoriteBuildingDetails()
    }

    private fun loadFavoriteBuildingDetails() {
        _favoriteBuildingDetails.value = listOf()
    }

    private fun loadRecentBuildingDetails() {
        _recentBuildingDetails.value = listOf(
            HouseSaleArticle(1, 5300, 10, 3, 123.0, 123.0, "강남구 역삼1동 멀티캠퍼스", 2003),
            HouseSaleArticle(1, 9800, 28, 15, 123.5, 123.0, "중구 신당동", 2013),
        )
    }
}