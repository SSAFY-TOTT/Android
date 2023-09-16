package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName

data class RegionPairResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
)
