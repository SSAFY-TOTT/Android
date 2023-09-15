package com.ssafy.tott.data.model.response


import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.HouseSaleArticle

data class HouseDetailResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("area")
    val area: Int,
    @SerializedName("buildingName")
    val buildingName: String,
    @SerializedName("builtYear")
    val builtYear: Int,
    @SerializedName("houseDetailId")
    val houseDetailId: Int,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("price")
    val price: Int,
    @SerializedName("floor")
    val floor: Int? = null,
    @SerializedName("wishlist")
    val wishlist: Boolean? = null,
) {
    fun toDomain() = HouseSaleArticle(
        id = houseDetailId,
        price = price,
        area = area,
        floor = floor,
        lat = lat,
        lng = lng,
        address = "$address $buildingName",
        built = builtYear,
        isWish = wishlist ?: false
    )
}