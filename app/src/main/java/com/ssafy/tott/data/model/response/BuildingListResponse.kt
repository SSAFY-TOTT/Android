package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName

data class BuildingListResponse(
    @SerializedName("response")
    val buildingListResponse: List<BuildingResponse>
)
