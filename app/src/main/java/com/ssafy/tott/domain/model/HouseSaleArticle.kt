package com.ssafy.tott.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HouseSaleArticle(
    val id: Int,
    val price: Int, // 가격 단위: 만원
    val area: Int, // 면적 단위: 평
    val floor: Int?,
    val lat: Double,
    val lng: Double,
    val address: String,
    val built: Int,
    val isWish: Boolean = false,
) : Parcelable