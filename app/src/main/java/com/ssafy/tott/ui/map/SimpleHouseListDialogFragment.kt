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
import dagger.hilt.android.AndroidEntryPoint

const val ARG_ITEM_COUNT = "item_count"

@AndroidEntryPoint
class SimpleHouseListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: RvBuildingDetailBinding? = null
    private val binding get() = _binding!!

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
        val adapter = BuildingDetailListAdapter()
        //TODO 임시 데이터
        adapter.submitList(listOf())
        binding.rvSimpleHouse.layoutManager = LinearLayoutManager(context)
        binding.rvSimpleHouse.adapter = adapter
    }

    companion object {
        fun newInstance(itemCount: Int): SimpleHouseListDialogFragment =
            SimpleHouseListDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

        const val TAG = "ModalBottomSheet"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}