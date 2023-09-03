package com.ssafy.tott.ui.model

// UI에 사용할 매물 데이터
data class BuildingDetailUI(
    val id: Int,
    val price: Int, // 가격 단위: 백만원
    val area: Int, // 면적 단위: 평
    val floor: Int?,
    val lat: Double,
    val lng: Double,
    val simpleAddress: String,
)