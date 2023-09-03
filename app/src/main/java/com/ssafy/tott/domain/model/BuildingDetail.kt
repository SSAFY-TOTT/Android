package com.ssafy.tott.domain.model

data class BuildingDetail(
    val id: Int,
    val price: Int, // 가격 단위: 백만원
    val area: Int, // 면적 단위: 평
    val floor: Int?,
)