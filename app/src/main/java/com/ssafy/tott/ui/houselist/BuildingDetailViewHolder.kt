package com.ssafy.tott.ui.houselist

import androidx.recyclerview.widget.RecyclerView
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ItemBuildingDetailBinding
import com.ssafy.tott.ui.model.BuildingDetailUI

class BuildingDetailViewHolder(private val binding: ItemBuildingDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BuildingDetailUI) {
        binding.root.context.run {
            binding.tvAreaItemBuildingDetail.text =
                getString(R.string.area_buildingDetail_item, item.area)
            binding.tvAddressItemBuildingDetail.text =
                getString(R.string.address_buildingDetail_item, item.simpleAddress)
            binding.tvPriceItemBuildingDetail.text =
                getString(R.string.price_buildingDetail_item, item.price)
        }
    }
}