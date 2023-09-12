package com.ssafy.tott.domain.model

import com.google.android.gms.maps.model.LatLng

//건물
data class Building(
    val lat: Double,
    val lng: Double,
    val buildingDetails: List<BuildingDetail>,
    val address: String,
    val built: Int,
    val isWish: Boolean = false,
) {
    val latLng = LatLng(lat, lng)

    fun toHouseSaleArticle() = buildingDetails.map {
        HouseSaleArticle(
            id = it.id,
            price = it.price,
            area = it.area,
            floor = it.floor,
            lat = lat,
            lng = lng,
            address = address,
            built = built,
            isWish = isWish,
        )
    }
}