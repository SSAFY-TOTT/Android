package com.ssafy.tott.ui.buildingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.model.ComprehensiveBudget
import com.ssafy.tott.domain.usecase.GetComprehensiveMoneyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildingDetailViewModel @Inject constructor(
    private val getComprehensiveMoneyUseCase: GetComprehensiveMoneyUseCase
) : ViewModel() {
    private val _budget = MutableLiveData<ComprehensiveBudget>(ComprehensiveBudget(0, 0, 0))
    val budget:LiveData<ComprehensiveBudget> = _budget

    private val _uiError = MutableStateFlow<Throwable?>(null)
    val uiError = _uiError.asStateFlow()

    fun setCreditLine(price: Int) {
        viewModelScope.launch {
            getComprehensiveMoneyUseCase(minOf(price, 21000)).collect { result ->
                result.onSuccess {
                    _budget.value = it
                }.onFailure {
                    _uiError.value = it
                }
            }
        }
    }
}