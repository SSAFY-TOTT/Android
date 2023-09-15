package com.ssafy.tott.domain.usecase

import com.ssafy.tott.data.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepositoryImpl) {
    operator fun invoke(id: String, password: String): Flow<Result<Unit>> = flow {
        userRepository.login(id, password).collect { result ->
            result.onSuccess {
                userRepository.saveToken(it).collect {
                    emit(Result.success(Unit))
                }
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}