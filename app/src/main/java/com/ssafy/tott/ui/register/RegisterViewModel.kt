package com.ssafy.tott.ui.register

import androidx.lifecycle.ViewModel
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.usecase.RegisterUserUseCase
import com.ssafy.tott.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUserUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Success(Unit))
    val uiState = _uiState.asStateFlow()
    fun makeCertNum(registerUser: RegisterUser) {
        _uiState.value = UiState.Loading
        registerUseCase.invoke(registerUser).onSuccess {
            _uiState.value = UiState.Success(Unit)
        }.onFailure {
            _uiState.value = UiState.Error(it)
        }
    }
}