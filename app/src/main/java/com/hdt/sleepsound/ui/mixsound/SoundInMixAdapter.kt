package com.hdt.sleepsound.ui.mixsound

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hdt.sleepsound.data.model.SoundModel
import com.hdt.sleepsound.databinding.ItemSoundBinding
import com.hdt.sleepsound.utils.extensions.loadImage

class SoundInMixAdapter(
    private val onClickItem: (SoundModel) -> Unit,
) : ListAdapter<SoundModel, SoundInMixAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SoundModel>() {
        override fun areItemsTheSame(oldItem: SoundModel, newItem: SoundModel): Boolean {
            return oldItem.imageSound == newItem.imageSound
        }

        override fun areContentsTheSame(oldItem: SoundModel, newItem: SoundModel): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position), onClickItem)
    }

    class ViewHolder(private val binding: ItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: SoundModel, onSelectedItem: (SoundModel) -> Unit) {
            binding.ivSound.loadImage(item.imageSound)
            binding.tvNameSound.text = binding.root.context.getString(item.nameSound)
            binding.root.isActivated = item.isSelected
            binding.ivSelected.isVisible = item.isSelected
            binding.lottiePlaying.isVisible = item.isSelected
            itemView.setOnClickListener {
                onSelectedItem(item)
            }
        }
    }
}