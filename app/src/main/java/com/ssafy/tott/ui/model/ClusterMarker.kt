package com.ssafy.tott.ui.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.ssafy.tott.domain.model.Building
import com.ssafy.tott.domain.model.BuildingDetail

data class ClusterMarker(
    val building: Building,
    val buildingDetails: List<BuildingDetail>,
    private val markerSnippet: String? = building.simpleAddress,
    private val markerTitle: String? = building.buildingName,
) : ClusterItem {
    private val simpleAddress get() = building.simpleAddress

    override fun getPosition(): LatLng = building.latLng
    override fun getSnippet(): String? = markerSnippet
    override fun getZIndex(): Float? = null
    override fun getTitle(): String? = markerTitle
}