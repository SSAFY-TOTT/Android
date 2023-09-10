package com.ssafy.tott.ui.houselist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ItemBuildingDetailBinding
import com.ssafy.tott.ui.model.BuildingDetailUI
import com.ssafy.tott.ui.util.loadImage

class BuildingDetailListAdapter(private val clickListener: (buildingDetailUI: BuildingDetailUI) -> Unit) :
    ListAdapter<BuildingDetailUI, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBuildingDetailBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = BuildingDetailViewHolder(binding)
        binding.root.setOnClickListener {
            Log.d("SimpleHouseListAdapter", it.toString())
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener(getItem(position))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BuildingDetailViewHolder -> holder.bind(getItem(position))
        }
    }

    private class BuildingDetailViewHolder(private val binding: ItemBuildingDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BuildingDetailUI) {
            binding.root.context.run {
                binding.tvAreaItemBuildingDetail.text =
                    getString(R.string.area_buildingDetail_item, item.area)
                binding.tvAddressItemBuildingDetail.text =
                    getString(R.string.address_buildingDetail_item, item.simpleAddress)
                binding.tvPriceItemBuildingDetail.text =
                    getString(R.string.price_buildingDetail_item, item.price)
                // TODO: 실제 사진으로 변경
                binding.ivRoomItemBuildingDetail.loadImage("https://user-images.githubusercontent.com/60271512/265007361-c1afed6e-303d-4cd4-a0e6-3e719116011b.png")
            }
        }
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<BuildingDetailUI>() {
            override fun areItemsTheSame(
                oldItem: BuildingDetailUI,
                newItem: BuildingDetailUI
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: BuildingDetailUI,
                newItem: BuildingDetailUI
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}