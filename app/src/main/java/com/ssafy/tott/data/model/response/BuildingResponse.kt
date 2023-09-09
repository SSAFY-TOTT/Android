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
    @SerializedName("legalDongName")
    val legalDongName: String,
    @SerializedName("districtName")
    val districtName: String,
    @SerializedName("buildingName")
    val buildingName: String,
    @SerializedName("built")
    val built: Int,
) {
    fun toDomain() = Building(
        districtName = districtName,
        legalDongName = legalDongName,
        buildingDetails = buildingDetails.map(BuildingDetailResponse::toDomain),
        buildingName = "$districtName $legalDongName $buildingName",
        lat = lat,
        lng = lng,
        built = built,
    )
}