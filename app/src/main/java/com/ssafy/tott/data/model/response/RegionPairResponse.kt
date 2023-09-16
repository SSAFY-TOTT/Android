package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.RegionPair

data class RegionPairResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: Int,
) {
    companion object {
        fun RegionPairResponse.toDomain() = RegionPair(
            name = name,
            code = code,
        )
    }
}
