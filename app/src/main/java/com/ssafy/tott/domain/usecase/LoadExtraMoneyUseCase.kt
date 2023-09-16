package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.model.ExtraMoney
import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadExtraMoneyUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Result<List<ExtraMoney>>> {
        return userRepository.getBudget()
    }
}