package com.ssafy.tott.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssafy.tott.databinding.RvBuildingDetailBinding
import com.ssafy.tott.ui.houselist.BuildingDetailListAdapter
import com.ssafy.tott.ui.model.BuildingDetailUI
import com.ssafy.tott.ui.util.parcelableArray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleHouseListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: RvBuildingDetailBinding? = null
    private val binding get() = _binding!!
    private var buildingDetailUIArray: Array<BuildingDetailUI>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RvBuildingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycleView()
    }

    private fun initRecycleView() {
        buildingDetailUIArray =
            arguments?.parcelableArray(BUILDING_DETAIL_UI_TAG, BuildingDetailUI::class.java)
        val adapter = BuildingDetailListAdapter()
        adapter.submitList(buildingDetailUIArray?.toList())
        binding.rvSimpleHouse.layoutManager = LinearLayoutManager(context)
        binding.rvSimpleHouse.adapter = adapter
    }

    companion object {
        fun newInstance(items: Array<BuildingDetailUI>): SimpleHouseListDialogFragment =
            SimpleHouseListDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(BUILDING_DETAIL_UI_TAG, items)
                }
            }

        const val BUILDING_lIST_MODAL_TAG = "BUILDING_lIST_MODAL_TAG"
        private const val BUILDING_DETAIL_UI_TAG = "BUILDING_DETAIL_UI"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}