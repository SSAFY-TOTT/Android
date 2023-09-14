package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.model.RegisterUser
import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(registerUser: RegisterUser): Flow<Result<Unit>> {
        return userRepository.makeCertNum(registerUser)
    }
}