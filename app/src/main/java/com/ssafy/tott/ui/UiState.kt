package com.ssafy.tott.ui


sealed class UiState {
    data object Loading : UiState()
    data class Error<E : Throwable>(val data: E) : UiState()
    data class Success<R>(val data: R) : UiState()
}