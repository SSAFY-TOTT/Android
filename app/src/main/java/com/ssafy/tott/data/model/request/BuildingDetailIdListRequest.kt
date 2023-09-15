package com.ssafy.tott.data.model.request


import com.google.gson.annotations.SerializedName

data class BuildingDetailIdListRequest(
    @SerializedName("houseDetailIdList")
    val houseDetailIdList: List<Int>
)