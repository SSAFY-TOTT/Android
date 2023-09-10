package com.ssafy.tott.ui.houselist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.tott.databinding.ItemBuildingDetailBinding
import com.ssafy.tott.ui.model.BuildingDetailUI

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