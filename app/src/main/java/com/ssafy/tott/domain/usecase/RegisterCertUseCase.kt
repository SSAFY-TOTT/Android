package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterCertUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(accountNum: String, certNum: String): Flow<Result<Unit>> {
        return userRepository.requestCert(accountNum, certNum)
    }
}