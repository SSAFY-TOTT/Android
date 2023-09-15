package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthTokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(isAccessToken: Boolean): Flow<Result<String>> =
        if (isAccessToken) {
            userRepository.getAccessToken()
        } else {
            userRepository.getRefreshToken()
        }
}