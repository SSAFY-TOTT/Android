package com.ssafy.tott.ui.buildingdetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ActivityBuildingDetailBinding
import com.ssafy.tott.ui.model.BuildingDetailUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildingDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityBuildingDetailBinding
    private var buildingDetailUI: BuildingDetailUI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuildingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildingDetailUI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TAG_BUILDING_DETAIL, BuildingDetailUI::class.java)
        } else {
            intent.getParcelableExtra(TAG_BUILDING_DETAIL)
        }
        initLayout()
        initMap()
    }

    private fun initLayout() {
        buildingDetailUI?.run {
            binding.tvAddressBuildingDetail.text = simpleAddress
            binding.tvAreaBuildingDetail.text = getString(R.string.area_buildingDetail_item, area)
            binding.tvPriceBuildingDetail.text =
                getString(R.string.price_buildingDetail_item, price)
            if (floor == null) {
                binding.tvFloorBuildingDetail.visibility = View.INVISIBLE
            } else {
                binding.tvFloorBuildingDetail.text =
                    getString(R.string.floor_buildingDetail_item, floor)
            }
            binding.tvBuiltBuildingDetail.text =
                getString(R.string.built_buildingDetail_item, built)
        }
    }

    private fun initMap() {
        MapsInitializer.initialize(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_buildingDetail) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("BuildingDetailActivity", "onMapReady: start")
        map = googleMap
        map.uiSettings.isMapToolbarEnabled = false
        map.setOnMapClickListener { }
        buildingDetailUI?.run {
            Log.d("BuildingDetailActivity", "onMapReady: $buildingDetailUI")
            val latLng = LatLng(lat, lng)
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            map.addMarker {
                position(latLng)
                title(simpleAddress)
            }
        }
    }

    companion object {
        const val TAG_BUILDING_DETAIL = "TAG_BUILDING_DETAIL"
    }
}