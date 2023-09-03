package com.ssafy.tott.domain.model

import com.google.android.gms.maps.model.LatLng

data class Building(
    val lat: Double,
    val lng: Double,
    val buildingDetails: List<BuildingDetail>,
    val legalDongName: String,
    val districtName: String,
    val buildingName: String,
) {
    val latLng = LatLng(lat, lng)
    val simpleAddress = "$districtName $legalDongName $buildingName"
}