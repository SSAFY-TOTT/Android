package com.ssafy.tott.data.model.response

import com.ssafy.tott.domain.model.BuildingDetail

data class BuildingDetailResponse(
    val id: Int,
    val price: Int, // 가격 단위: 천만원
    val area: Int, // 면적 단위: 평
    val floor: Int?,
) {
    fun toDomain() = BuildingDetail(
        id = id,
        price = price,
        area = area,
        floor = floor,
    )
}
