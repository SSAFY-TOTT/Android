package com.ssafy.tott.ui.home

import androidx.lifecycle.ViewModel
import com.ssafy.tott.ui.model.BuildingDetailUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _recentBuildingDetails = MutableStateFlow<List<BuildingDetailUI>>(listOf())
    val recentBuildingDetails = _recentBuildingDetails.asStateFlow()

    private val _favoriteBuildingDetails = MutableStateFlow<List<BuildingDetailUI>>(listOf())
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
            BuildingDetailUI(1, 5300, 10, 3, 123.0, 123.0, "강남구 역삼1동 멀티캠퍼스", 2003),
            BuildingDetailUI(1, 9800, 28, 15, 123.5, 0.0, "중구 신당동", 2013),
        )
    }
}