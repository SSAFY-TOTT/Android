package com.ssafy.tott.ui.houselist

import androidx.recyclerview.widget.RecyclerView
import com.ssafy.tott.databinding.ItemBuildingDetailBinding
import com.ssafy.tott.ui.model.BuildingDetailUI

class BuildingDetailViewHolder(private val binding: ItemBuildingDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BuildingDetailUI) {
        binding.tvSimpleHouseItem.text = item.toString()
    }
}