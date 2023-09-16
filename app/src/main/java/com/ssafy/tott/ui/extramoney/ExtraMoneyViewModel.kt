package com.ssafy.tott.ui.extramoney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.model.ExtraMoney
import com.ssafy.tott.domain.usecase.LoadExtraMoneyUseCase
import com.ssafy.tott.domain.usecase.SaveExtraMoneyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtraMoneyViewModel @Inject constructor(
    private val saveExtraMoneyUseCase: SaveExtraMoneyUseCase,
    private val loadExtraMoneyUseCase: LoadExtraMoneyUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Wait)
    val uiState = _uiState.asStateFlow()

    sealed class UiState {
        data object Wait : UiState()
        data object Loading : UiState()
        data class Error(val throwable: Throwable) : UiState()
        data object Success : UiState()
    }

    private val _extraMoneyList = MutableStateFlow<List<ExtraMoney>>(emptyList())
    val extraMoneyList = _extraMoneyList.asStateFlow()

    init {
        loadExtraMoney()
    }

    fun saveExtraMoneyList(
        vararg pair: Pair<String, Int>
    ) {
        val list = pair.filter { it.first.isNotBlank() }.map { ExtraMoney(it.first, it.second) }
        if (list.isEmpty()) return
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            saveExtraMoneyUseCase(list).collect { result ->
                result.onSuccess {
                    _uiState.value = UiState.Success
                }.onFailure {
                    _uiState.value = UiState.Error(it)
                }
            }
        }
    }

    private fun loadExtraMoney() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            loadExtraMoneyUseCase().collect { result ->
                result.onSuccess {
                    _uiState.value = UiState.Success
                    _extraMoneyList.value = it
                }.onFailure {
                    _uiState.value = UiState.Error(it)
                }
            }
        }
    }
}