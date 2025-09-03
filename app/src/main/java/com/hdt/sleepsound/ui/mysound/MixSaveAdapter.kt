package com.hdt.sleepsound.ui.mysound

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hdt.sleepsound.data.model.MixModel
import com.hdt.sleepsound.databinding.ItemMySoundBinding

class MixSaveAdapter (
    private val onClickItem: (MixModel) -> Unit,
) : ListAdapter<MixModel, MixSaveAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<MixModel>() {
        override fun areItemsTheSame(oldItem: MixModel, newItem: MixModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MixModel, newItem: MixModel): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMySoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position), onClickItem)
    }

    class ViewHolder(private val binding: ItemMySoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: MixModel, onSelectedItem: (MixModel) -> Unit) {
            binding.tvMySound.text = item.name
            itemView.setOnClickListener {
                onSelectedItem(item)
            }
        }
    }
}