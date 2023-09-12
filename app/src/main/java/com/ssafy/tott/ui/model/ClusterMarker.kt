package com.ssafy.tott.ui.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.ssafy.tott.domain.model.Building

data class ClusterMarker(
    val building: Building,
    private val markerSnippet: String? = building.address,
    private val markerTitle: String? = building.address,
) : ClusterItem {
    override fun getPosition(): LatLng = building.latLng
    override fun getSnippet(): String? = markerSnippet
    override fun getZIndex(): Float? = null
    override fun getTitle(): String? = markerTitle
}