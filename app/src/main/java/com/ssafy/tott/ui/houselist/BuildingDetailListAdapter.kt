package com.ssafy.tott.ui.houselist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.tott.R
import com.ssafy.tott.databinding.ItemBuildingDetailBinding
import com.ssafy.tott.domain.model.HouseSaleArticle
import com.ssafy.tott.ui.util.loadImage
import com.ssafy.tott.ui.util.loadRandomImage

class BuildingDetailListAdapter(private val clickListener: (buildingDetailUI: HouseSaleArticle) -> Unit) :
    ListAdapter<HouseSaleArticle, RecyclerView.ViewHolder>(diffUtil) {
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
        fun bind(item: HouseSaleArticle) {
            binding.root.context.run {
                binding.tvAreaItemBuildingDetail.text =
                    getString(R.string.area_buildingDetail_item, item.area)
                binding.tvAddressItemBuildingDetail.text =
                    getString(R.string.address_buildingDetail_item, item.address)
                binding.tvPriceItemBuildingDetail.text =
                    getString(R.string.price_buildingDetail_item, item.price)
                binding.ivRoomItemBuildingDetail.loadRandomImage()
            }
        }
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<HouseSaleArticle>() {
            override fun areItemsTheSame(
                oldItem: HouseSaleArticle,
                newItem: HouseSaleArticle
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HouseSaleArticle,
                newItem: HouseSaleArticle
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}