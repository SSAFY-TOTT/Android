package com.ssafy.tott.ui.model

import android.os.Parcelable
import com.ssafy.tott.domain.model.Building
import kotlinx.parcelize.Parcelize

@Parcelize
// UI에 사용할 매물 데이터
data class BuildingDetailUI(
    val id: Int,
    val price: Int, // 가격 단위: 백만원
    val area: Int, // 면적 단위: 평
    val floor: Int?,
    val lat: Double,
    val lng: Double,
    val simpleAddress: String,
    val built: Int,
) : Parcelable {
    companion object {
        fun Building.toBuildingDetailUIList() = buildingDetails.map {
            BuildingDetailUI(
                id = it.id,
                price = it.price,
                area = it.area,
                floor = it.floor,
                lat = lat,
                lng = lng,
                simpleAddress = simpleAddress,
                built = built,
            )
        }
    }
}