package com.ssafy.tott.ui.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.ssafy.tott.domain.model.House

data class ClusterMarker(
    val lat: Double,
    val lng: Double,
    private val markerSnippet: String?,
    private val markerTitle: String?,
) : ClusterItem {
    constructor(
        latLng: LatLng, snippet: String?, title: String?
    ) : this(latLng.latitude, latLng.longitude, snippet, title)

    private val latLng get() = LatLng(lat, lng)
    override fun getPosition(): LatLng = latLng
    override fun getSnippet(): String? = markerSnippet
    override fun getZIndex(): Float? {
        return null
    }

    override fun getTitle(): String? = markerTitle

    companion object {
        fun House.toClusterMarker(): ClusterMarker = ClusterMarker(
            lat = this.lat,
            lng = this.lng,
            markerSnippet = "snippet",
            markerTitle = toString(),
        )
    }
}