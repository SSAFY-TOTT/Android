package com.ssafy.tott.data.model.request

import com.ssafy.tott.domain.model.SearchFilter

data class SearchFilterRequest(
    val districtName: String,
    val legalDongName: String,
    val minPrice: Int,
    val maxPrice: Int,
    val minArea: Int,
    val maxArea: Int,
    val types: List<String>,
    val built: Int,
) {
    companion object {
        fun SearchFilter.toData() = SearchFilterRequest(
            districtName = districtName,
            legalDongName = legalDongName,
            minPrice = minPrice,
            maxPrice = maxPrice,
            minArea = minArea,
            maxArea = maxArea,
            types = type,
            built = built
        )
    }
}
