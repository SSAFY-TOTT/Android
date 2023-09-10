package com.ssafy.tott.ui.map

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
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
import com.ssafy.tott.ui.model.BuildingDetailUI.Companion.toBuildingDetailUIList
import com.ssafy.tott.ui.model.ClusterMarker
import com.ssafy.tott.ui.searchfilter.SearchFilterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: SearchMapViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var clusterManager: ClusterManager<ClusterMarker>
    private val binding: ActivitySearchMapBinding by lazy {
        ActivitySearchMapBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer_map_searchMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        SearchMapViewModel.BUILDING_TYPES =
            resources.getStringArray(R.array.building_types).toList()

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
            (supportFragmentManager.fragments.firstOrNull { it is SearchFilterFragment } as? SearchFilterFragment)?.savedFilterSetting()
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
        clusterManager = ClusterManager<ClusterMarker>(this, map)
        lifecycleScope.launch {
            val lat = 38.0
            val lng = 127.0
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 5F))
            map.clear()
            setUpCluster()
        }
    }

    private fun setUpCluster() {
        clusterManager.markerCollection.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoContents(marker: Marker): View {
                return View(applicationContext)
            }

            override fun getInfoWindow(marker: Marker): View {
                return View(applicationContext)
            }
        })
        observeBuildings()
        setupMapClickListener()
    }

    private fun setupMapClickListener() {
        clusterManager.setOnClusterItemClickListener {
            val modalBottomSheet = SimpleHouseListDialogFragment.newInstance(
                it.building.toBuildingDetailUIList().toTypedArray()
            )
            modalBottomSheet.show(
                supportFragmentManager,
                SimpleHouseListDialogFragment.BUILDING_lIST_MODAL_TAG
            )
            true
        }
    }

    private fun observeBuildings() {
        lifecycleScope.launch {
            viewModel.buildings.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { list ->
                    list.firstOrNull()?.let {
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(LatLng(it.lat, it.lng), 13f)
                        )
                    }
                    list.forEach {
                        clusterManager.addItem(ClusterMarker(it))
                    }
                    clusterManager.cluster()
                }
        }
    }
}