package com.ssafy.tott.ui.extramoney

import com.ssafy.tott.domain.model.ExtraMoney
import com.ssafy.tott.domain.usecase.SaveExtraMoneyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ExtraMoneyViewModel @Inject constructor(
    private val saveExtraMoneyUseCase: SaveExtraMoneyUseCase,
) {
    private val _extraMoneyList = MutableStateFlow<List<ExtraMoney>>(emptyList())
    val extraMoneyList = _extraMoneyList.asStateFlow()

    fun saveExtraMoneyList(
        vararg pair: Pair<String, Int>
    ) {
        val list = pair.filter { it.first.isNotBlank() }.map { ExtraMoney(it.first, it.second) }
        saveExtraMoneyUseCase(list)
    }
}