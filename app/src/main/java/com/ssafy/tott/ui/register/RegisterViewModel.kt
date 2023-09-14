package com.ssafy.tott.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.usecase.RegisterCertUseCase
import com.ssafy.tott.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUserUseCase, private val certUseCase: RegisterCertUseCase
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Error(val throwable: Throwable) : UiState()
        data object Success : UiState()
        data object Wait : UiState()
    }

    private val _uiState = Channel<UiState>()
    val uiState = _uiState.receiveAsFlow()

    private val _canCertUser: MutableLiveData<Boolean> = MutableLiveData(false)
    val canCertUser: LiveData<Boolean> = _canCertUser

    fun makeCertNum(registerUser: RegisterUser) {
        viewModelScope.launch {
            if (registerUser.isValid()) {
                _uiState.send(UiState.Error(Throwable("다시 입력해 주세요.")))
                return@launch
            }
            _uiState.send(UiState.Loading)
            registerUseCase.invoke(registerUser).collect { result ->
                result.onSuccess {
                    _uiState.send(UiState.Success)
                }.onFailure {
                    _uiState.send(UiState.Error(it))
                }
            }
        }
    }

    fun certUser(accountNum: String, certNum: String) {
        viewModelScope.launch {
            _uiState.send(UiState.Loading)
            certUseCase(accountNum, certNum).collect { result ->
                result.onSuccess {
                    _uiState.send(UiState.Success)
                }.onFailure {
                    _uiState.send(UiState.Error(it))
                }
            }
        }
    }

    suspend fun setUiStateToWait() {
        _uiState.send(UiState.Wait)
    }

    fun setCanCertUser(boolean: Boolean) {
        _canCertUser.value = boolean
    }
}