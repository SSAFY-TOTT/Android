package com.ssafy.tott.domain.model

//매물
data class BuildingDetail(
    val id: Int,
    val price: Int, // 가격 단위: 만원
    val area: Int, // 면적 단위: 평
    val floor: Int?,
    val isWish: Boolean = false,
)