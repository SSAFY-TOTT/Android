package com.ssafy.tott.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.model.HouseSaleArticle
import com.ssafy.tott.domain.usecase.GetRecentHouseArticlesUseCase
import com.ssafy.tott.domain.usecase.GetWishListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecentHouseArticlesUseCase: GetRecentHouseArticlesUseCase,
    private val getWishListUseCase: GetWishListUseCase,
) : ViewModel() {
    private val _recentBuildingDetails = MutableStateFlow<List<HouseSaleArticle>>(listOf())
    val recentBuildingDetails = _recentBuildingDetails.asStateFlow()

    private val _wishList = MutableStateFlow<List<HouseSaleArticle>>(listOf())
    val wishList = _wishList.asStateFlow()

    private val _errorState = MutableStateFlow<Exception?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        loadRecentBuildingDetails()
        loadWishList()
    }

    private fun loadWishList() {
        _wishList.value = listOf()
        viewModelScope.launch {
            getWishListUseCase().collect { result ->
                result.onSuccess {
                    _wishList.value = it
                }.onFailure {
                    _errorState.value = Exception(it.message)
                }
            }
        }
    }

    private fun loadRecentBuildingDetails() {
        // TODO 실제 조회한 매물 데이터로 수정 필요
        val ids: List<Int>? = listOf(232, 6, 32, 8, 2326, 2651, 31, 10, 67)
        viewModelScope.launch {
            if (ids.isNullOrEmpty()) {
                _recentBuildingDetails.value = emptyList()
            } else {
                getRecentHouseArticlesUseCase(ids).collect { result ->
                    result.onSuccess {
                        _recentBuildingDetails.value = it
                    }.onFailure {
                        _errorState.value = Exception(it.message)
                    }
                }
            }
        }
    }
}