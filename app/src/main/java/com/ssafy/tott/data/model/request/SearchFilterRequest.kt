package com.ssafy.tott.data.model.request

import com.google.gson.annotations.SerializedName
import com.ssafy.tott.domain.model.SearchFilter

data class SearchFilterRequest(
    @SerializedName("districtCode")
    val districtCode: Int,
    @SerializedName("legalDongCode")
    val legalDongCode: Int,
    @SerializedName("minPrice")
    val minPrice: Int,
    @SerializedName("maxPrice")
    val maxPrice: Int,
    @SerializedName("minArea")
    val minArea: Int,
    @SerializedName("maxArea")
    val maxArea: Int,
    @SerializedName("types")
    val types: List<String>,
    @SerializedName("buildingYear")
    val built: Int,
) {
    companion object {
        fun SearchFilter.toData() = SearchFilterRequest(
            districtCode = districtCode,
            legalDongCode = legalDongCode,
            minPrice = minPrice,
            maxPrice = maxPrice,
            minArea = minArea,
            maxArea = maxArea,
            types = type,
            built = built
        )
    }
}
