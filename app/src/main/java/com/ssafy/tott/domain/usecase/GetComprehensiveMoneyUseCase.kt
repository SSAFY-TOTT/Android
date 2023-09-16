package com.ssafy.tott.domain.usecase

import com.ssafy.tott.domain.model.ComprehensiveBudget
import com.ssafy.tott.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComprehensiveMoneyUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(price: Int?): Flow<Result<ComprehensiveBudget>> =
        userRepository.getComprehensiveBudget(price ?: 0)
}