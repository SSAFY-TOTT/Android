package com.ssafy.tott.data.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.Building

data class BuildingResponse(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("buildingDetails")
    val buildingDetails: List<BuildingDetailResponse>,
    @SerializedName("buildingName")
    val buildingName: String,
    @SerializedName("built")
    val built: Int,
) {
    fun toDomain() = Building(
        buildingDetails = buildingDetails.map(BuildingDetailResponse::toDomain),
        lat = lat,
        lng = lng,
        built = built,
        address = buildingName,
    )
}