package com.ssafy.tott.data.model.response


import com.google.gson.annotations.SerializedName

data class BuildingDetailsResponse(
    @SerializedName("houseDetailList")
    val houseDetailResponseList: List<HouseDetailResponse>,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("resultCount")
    val resultCount: Int,
)