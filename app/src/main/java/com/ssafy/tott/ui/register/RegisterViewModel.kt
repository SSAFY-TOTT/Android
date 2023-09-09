package com.ssafy.tott.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUserUseCase) :
    ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Error(val throwable: Throwable) : UiState()
        data object Success : UiState()
    }

    private val _uiState = Channel<UiState>()
    val uiState = _uiState.receiveAsFlow()

    fun makeCertNum(registerUser: RegisterUser) {
        viewModelScope.launch {
            if (registerUser.isValid()) {
                _uiState.send(UiState.Error(Throwable("다시 입력해 주세요.")))
                return@launch
            }
            _uiState.send(UiState.Loading)
            registerUseCase.invoke(registerUser).onSuccess {
                _uiState.send(UiState.Success)
            }.onFailure {
                _uiState.send(UiState.Error(it))
            }
        }
    }
}