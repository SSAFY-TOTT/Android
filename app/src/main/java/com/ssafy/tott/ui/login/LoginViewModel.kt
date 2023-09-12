package com.ssafy.tott.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    sealed class UiState {
        data object Loading : UiState()
        data class Error(val throwable: Throwable) : UiState()
        data object Success : UiState()
    }
}
