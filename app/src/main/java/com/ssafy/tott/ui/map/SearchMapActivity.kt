package com.ssafy.tott.ui.map

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.ssafy.tott.domain.model.SearchFilter
import com.ssafy.tott.ui.model.ClusterMarker
import com.ssafy.tott.ui.searchfilter.SearchFilterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivitySearchMapBinding
    private val modalBottomSheet = SimpleHouseListDialogFragment()

    private val resultActivity by lazyOf(initResultActivityCallBack())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer_searchMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        modalBottomSheet.show(supportFragmentManager, SimpleHouseListDialogFragment.TAG)

        initToolbar()
    }

    private fun initResultActivityCallBack(): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val districtName =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableExtra(
                            SearchFilterActivity.TAG_DISTRICT_NAME,
                            SearchFilter::class.java
                        )
                    } else {
                        result.data?.getParcelableExtra(SearchFilterActivity.TAG_DISTRICT_NAME)
                    }
                Log.d(this::class.simpleName, "initResultActivityCallBack : $districtName")
            }
        }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarSearchMap)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_map, menu)
        return true
    }

    fun menuInflate(id: Int) {
        val menu = binding.toolbarSearchMap.menu
        menu.clear()
        menuInflater.inflate(id, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            Log.d(this::class.simpleName, "onOptionsItemSelected: home")
            true
        }

        R.id.action_setFilter -> {
            Log.d(this::class.simpleName, "onOptionsItemSelected: filter")
            startFragment(SearchFilterFragment())
            true
        }

        else -> {
            Log.d(
                this::class.simpleName, "onOptionsItemSelected: else -> ${item.itemId}"
            )
            super.onOptionsItemSelected(item)
        }
    }

    private fun startFragment(fragment: Fragment) {
        Log.d(
            this::class.simpleName,
            "startFragment: ${supportFragmentManager.backStackEntryCount}"
        )
        menuInflate(R.menu.menu_toolbar_search_filter)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.layout_searchMap, fragment)
        transaction.commit()
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