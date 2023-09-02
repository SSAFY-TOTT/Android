package com.ssafy.tott.ui.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ActivitySearchMapBinding
import com.ssafy.tott.ui.model.ClusterMarker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivitySearchMapBinding
    private val modalBottomSheet = SimpleHouseListDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer_searchMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        modalBottomSheet.show(supportFragmentManager, SimpleHouseListDialogFragment.TAG)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        lifecycleScope.launch {
            val lat = 38.0
            val lng = 127.0
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 5F))
            map.clear()
            setUpCluster()
        }
    }

    private fun setUpCluster() {
        val clusterManager = ClusterManager<ClusterMarker>(this, map)

        clusterManager.markerCollection.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoContents(p0: Marker): View {
                return View(applicationContext)
            }

            override fun getInfoWindow(p0: Marker): View {
                return View(applicationContext)
            }
        })
        map.setOnCameraIdleListener(clusterManager)
//        setupMapClickListener(clusterManager)
    }
}