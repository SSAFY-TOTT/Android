package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.model.AuthToken
import com.ssafy.tott.domain.repository.UserRepository
import javax.inject.Inject

class SaveAuthTokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(authToken: AuthToken) {
        userRepository.saveToken(authToken)
    }
}