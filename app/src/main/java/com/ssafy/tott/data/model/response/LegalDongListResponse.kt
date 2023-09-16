package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName

data class LegalDongListResponse(
    @SerializedName("legalDongList")
    val list: List<RegionPairResponse>,
)