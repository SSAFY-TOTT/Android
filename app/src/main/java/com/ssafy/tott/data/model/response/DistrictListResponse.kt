package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName

data class DistrictListResponse(
    @SerializedName("districtList")
    val list: List<RegionPairResponse>,
)