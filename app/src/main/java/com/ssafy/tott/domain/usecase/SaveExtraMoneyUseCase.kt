package com.ssafy.tott.domain.usecase

import com.ssafy.tott.data.repository.UserRepositoryImpl
import com.ssafy.tott.domain.model.ExtraMoney
import javax.inject.Inject

class SaveExtraMoneyUseCase @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) {
    operator fun invoke(extraMoneyList: List<ExtraMoney>) =
        userRepositoryImpl.saveBudget(extraMoneyList)
}