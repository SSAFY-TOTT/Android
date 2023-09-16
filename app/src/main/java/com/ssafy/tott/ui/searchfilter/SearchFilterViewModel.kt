package com.ssafy.tott.ui.searchfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _districtMap = MutableLiveData<Map<String, Int>>(mapOf())
    val districtMap: LiveData<Map<String, Int>> = _districtMap

    private val _legalDongMap = MutableLiveData<Map<String, Int>>(mapOf())
    val legalDongMap: LiveData<Map<String, Int>> = _legalDongMap

    private val _uiError = MutableStateFlow<Throwable?>(null)
    val uiError = _uiError.asStateFlow()


    init {
        loadDistrict()
    }

    fun selectDistrict(name: String?) {
        val code = districtMap.value?.get(name ?: "") ?: -1
        loadLegalDongMap(code)
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
                    if (map.isNotEmpty()) {
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