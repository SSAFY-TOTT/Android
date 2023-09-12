package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName

data class BuildingListResponse(
    @SerializedName("houseGeoList")
    val buildingListResponse: List<BuildingResponse>,

    @SerializedName("regionName")
    val regionName: String,
)
