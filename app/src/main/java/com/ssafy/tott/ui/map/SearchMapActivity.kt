package com.ssafy.tott.ui.map

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
import com.ssafy.tott.ui.searchfilter.SearchFilterFragment
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
            .findFragmentById(R.id.fragmentContainer_map_searchMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        modalBottomSheet.show(supportFragmentManager, SimpleHouseListDialogFragment.TAG)

        initToolbar()
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
            Log.d(
                this::class.simpleName,
                "onOptionsItemSelected: home ${supportFragmentManager.backStackEntryCount}"
            )
            onBackPressed()
            true
        }

        R.id.action_setFilter -> {
            Log.d(this::class.simpleName, "onOptionsItemSelected: filter")
            startFragment(SearchFilterFragment())
            true
        }

        R.id.action_saveFilter -> {
            Log.d(this::class.simpleName, "onOptionsItemSelected: save")
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