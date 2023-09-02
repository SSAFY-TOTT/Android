package com.ssafy.tott.ui.houselist

import androidx.recyclerview.widget.RecyclerView
import com.ssafy.tott.databinding.ItemSimpleHouseBinding
import com.ssafy.tott.domain.model.SimpleHouse

class SimpleHouseViewHolder(private val binding: ItemSimpleHouseBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SimpleHouse) {
        binding.tvSimpleHouseItem.text = item.toString()
    }
}