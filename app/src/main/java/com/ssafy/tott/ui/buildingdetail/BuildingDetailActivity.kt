package com.ssafy.tott.ui.buildingdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.ktx.addMarker
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ActivityBuildingDetailBinding
import com.ssafy.tott.domain.model.HouseSaleArticle
import com.ssafy.tott.ui.util.getParcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuildingDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: BuildingDetailViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityBuildingDetailBinding
    private var buildingDetailUI: HouseSaleArticle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuildingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildingDetailUI =
            intent.getParcelable(TAG_BUILDING_DETAIL, HouseSaleArticle::class.java)
        initLayout()
        initMap()
        initToolbar()
        initObserve()
        initButton()
    }

    private fun initButton() {
        binding.btnLinkBuildingDetail.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://m.shinhan.com/rib/mnew/index.jsp#220011120000")
            )
            startActivity(intent)
        }
    }

    private fun initLayout() {
        buildingDetailUI?.run {
            binding.tvAddressBuildingDetail.text = address
            binding.tvAreaBuildingDetail.text = getString(R.string.area_buildingDetail_item, area)
            binding.tvPriceFixBuildingDetail.text =
                getString(R.string.price_buildingDetail_item, price)
            if (floor == null) {
                binding.tvFloorBuildingDetail.visibility = View.INVISIBLE
            } else {
                binding.tvFloorBuildingDetail.text =
                    getString(R.string.floor_buildingDetail_item, floor)
            }
            binding.tvBuiltBuildingDetail.text =
                getString(R.string.built_buildingDetail_item, built)
            viewModel.setCreditLine(price)
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
                title(address)
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarBuildingDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_building_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            Log.d(
                this::class.simpleName,
                "onOptionsItemSelected: home ${supportFragmentManager.backStackEntryCount}"
            )
            onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiError.flowWithLifecycle(
                lifecycle, Lifecycle.State.STARTED
            ).collect {
                if (it != null) {
                    Snackbar.make(binding.root, it.message ?: "오류 발생", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.budget.observe(this@BuildingDetailActivity) { budget ->
                binding.tvRentPriceBuildingDetail.text =
                    resources.getString(
                        R.string.rent_price_buildingDetail,
                        budget.creditLine / 10000
                    )
                binding.tvRateInterestBuildingDetail.text =
                    resources.getString(
                        R.string.rate_rent_buildingDetail,
                        (budget.creditLine * 5.58 / 100).toInt()
                    )
            }
        }
    }

    companion object {
        const val TAG_BUILDING_DETAIL = "TAG_BUILDING_DETAIL"
    }
}