package com.ssafy.tott.domain.usecase

import com.ssafy.tott.data.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val accountRepository: UserRepositoryImpl) {
    operator fun invoke(id: String, password: String): Flow<Result<Unit>> = flow {
        accountRepository.login(id, password).collect { result ->
            result.onSuccess {
                emit(Result.success(Unit))
                //TODO 토큰 저장 기능 추가
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }
}