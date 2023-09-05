package com.ssafy.tott.domain.model

data class SearchFilter(
    val districtName: String,
    val legalDongName: String,
    val minPrice: Int,
    val maxPrice: Int,
    val minArea: Int,
    val maxArea: Int,
    val type: List<String>,
    val built: Int,
)