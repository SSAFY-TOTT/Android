package com.ssafy.tott.domain.model

data class Region(
    val districtName: String,
    val code: Int,
    val legalDong: Map<String, Int>
)