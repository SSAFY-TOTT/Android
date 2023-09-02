package com.ssafy.tott.ui.houselist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.tott.databinding.ItemSimpleHouseBinding
import com.ssafy.tott.domain.model.SimpleHouse

class SimpleHouseListAdapter : ListAdapter<SimpleHouse, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSimpleHouseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = SimpleHouseViewHolder(binding)
        binding.root.setOnClickListener {
            Log.d("SimpleHouseListAdapter", it.toString())
            // TODO 리스트 클릭
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SimpleHouseViewHolder -> holder.bind(getItem(position))
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SimpleHouse>() {
            override fun areItemsTheSame(oldItem: SimpleHouse, newItem: SimpleHouse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SimpleHouse, newItem: SimpleHouse): Boolean {
                return oldItem == newItem
            }
        }
    }
}