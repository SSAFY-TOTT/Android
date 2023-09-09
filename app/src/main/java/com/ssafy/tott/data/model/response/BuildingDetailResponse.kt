package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.BuildingDetail

data class BuildingDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int, // 가격 단위: 천만원
    @SerializedName("area")
    val area: Int, // 면적 단위: 평
    @SerializedName("floor")
    val floor: Int?,
) {
    fun toDomain() = BuildingDetail(
        id = id,
        price = price,
        area = area,
        floor = floor,
    )
}
