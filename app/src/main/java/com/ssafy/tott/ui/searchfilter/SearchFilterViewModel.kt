package com.ssafy.tott.ui.searchfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.usecase.GetDistrictListUseCase
import com.ssafy.tott.domain.usecase.GetLegalDongListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val getDistrictUseCase: GetDistrictListUseCase,
    private val getLegalDongUseCase: GetLegalDongListUseCase,

    ) : ViewModel() {
    private val _districtMap = MutableStateFlow<Map<String, Int>>(mapOf())
    val districtMap = _districtMap.asStateFlow()

    private val _legalDongMap = MutableStateFlow<Map<String, Int>>(mapOf())
    val legalDongMap = _districtMap.asStateFlow()

    private val _uiError = MutableStateFlow<Throwable?>(null)
    val uiError = _uiError.asStateFlow()

    init {
        loadDistrict()
    }

    private fun loadDistrict() {
        viewModelScope.launch {
            getDistrictUseCase().collect { districtResult ->
                districtResult.onSuccess {
                    _districtMap.value = it
                    val firstDistrictCode = it.values.firstOrNull()
                    if (firstDistrictCode == null) {
                        _uiError.value = Exception("데이터가 없습니다.")
                    } else {
                        loadLegalDongMap(firstDistrictCode)
                    }
                }.onFailure {
                    _uiError.value = it
                }
            }
        }
    }

    fun loadLegalDongMap(districtCode: Int) {
        viewModelScope.launch {
            getLegalDongUseCase(districtCode).collect { dongResult ->
                dongResult.onSuccess { map ->
                    if (map.isEmpty()) {
                        _legalDongMap.value = map
                    } else {
                        _uiError.value = Exception("데이터가 없습니다.")
                    }
                }.onFailure {
                    _uiError.value = it
                }
            }
        }
    }
}