package com.ssafy.tott.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.tott.databinding.ActivityHomeBinding
import com.ssafy.tott.ui.houselist.BuildingDetailListAdapter
import com.ssafy.tott.ui.model.BuildingDetailUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecycleView()
    }

    private fun initRecycleView() {
        val adapter = BuildingDetailListAdapter()
        //TODO 임시 데이터
        adapter.submitList(
            listOf(
                BuildingDetailUI(1, 5300, 10, 3, 123.0, 123.0, "강남구 역삼1동 멀티캠퍼스", 2003),
                BuildingDetailUI(1, 9800, 28, 15, 123.5, 0.0, "중구 신당동", 2013),
            )
        )
        binding.rvRecentViewListMain.adapter = adapter
    }
}