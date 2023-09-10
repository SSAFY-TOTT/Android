package com.ssafy.tott.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ssafy.tott.databinding.ActivityHomeBinding
import com.ssafy.tott.ui.buildingdetail.BuildingDetailActivity
import com.ssafy.tott.ui.houselist.BuildingDetailListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecycleView()
    }

    private fun initRecycleView() {
        val recentListAdapter = BuildingDetailListAdapter {
            val intent = Intent(this, BuildingDetailActivity::class.java)
            intent.putExtra(BuildingDetailActivity.TAG_BUILDING_DETAIL, it)
            startActivity(intent)
        }
        binding.rvRecentViewListMain.adapter = recentListAdapter

        val favoriteListAdapter = BuildingDetailListAdapter {
            val intent = Intent(this, BuildingDetailActivity::class.java)
            intent.putExtra(BuildingDetailActivity.TAG_BUILDING_DETAIL, it)
            startActivity(intent)
        }
        binding.rvFavoriteViewListMain.adapter = favoriteListAdapter

        lifecycleScope.launch {
            viewModel.recentBuildingDetails.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.tvEmptyRecentViewListMain.visibility = if (it.isNotEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                    recentListAdapter.submitList(it)
                }
            viewModel.favoriteBuildingDetails.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.tvEmptyFavoriteViewListMain.visibility = if (it.isNotEmpty()) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                    favoriteListAdapter.submitList(it)
                }
        }
    }
}