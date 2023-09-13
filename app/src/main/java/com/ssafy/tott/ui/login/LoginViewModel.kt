package com.ssafy.tott.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginState = MutableStateFlow<UiState>(UiState.Wait)
    val loginState = _loginState.asStateFlow()

    sealed class UiState {
        data object Wait : UiState()
        data object Loading : UiState()
        data class Error(val throwable: Throwable) : UiState()
        data object Success : UiState()
    }

    fun login(id: String, password: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            loginUseCase.invoke(id, password).collect { result ->
                result.onSuccess {
                    _loginState.value = UiState.Success
                }.onFailure {
                    _loginState.value = UiState.Error(it)
                }
            }
        }
    }
}
