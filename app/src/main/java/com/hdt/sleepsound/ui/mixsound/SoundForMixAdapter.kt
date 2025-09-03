package com.hdt.sleepsound.ui.mixsound

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hdt.sleepsound.data.model.SoundModel
import com.hdt.sleepsound.databinding.ItemSoundForMixBinding
import com.hdt.sleepsound.utils.extensions.loadImage

class SoundForMixAdapter(
    private val onClickItem: (SoundModel) -> Unit
) : ListAdapter<SoundModel, SoundForMixAdapter.ViewHolder>(DiffCallback) {

    companion object {
        private const val PAYLOAD_SELECTION = "PAYLOAD_SELECTION"

        private val DiffCallback = object : DiffUtil.ItemCallback<SoundModel>() {
            override fun areItemsTheSame(oldItem: SoundModel, newItem: SoundModel): Boolean {
                return oldItem.imageSound == newItem.imageSound
            }

            override fun areContentsTheSame(oldItem: SoundModel, newItem: SoundModel): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: SoundModel, newItem: SoundModel): Any? {
                // Kiểm tra nếu chỉ có trạng thái isSelected thay đổi
                return if (oldItem.isSelected != newItem.isSelected) PAYLOAD_SELECTION else null
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).imageSound.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSoundForMixBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            holder.bindView(getItem(position), onClickItem)
        } else {
            if (payloads.contains(PAYLOAD_SELECTION)) {
                holder.updateSelection(getItem(position).isSelected)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position), onClickItem)
    }

    // Phương thức này không còn cần thiết vì việc toggle được xử lý ở adapter cha
    // fun toggleSelection(soundModel: SoundModel) {
    //    ...
    // }

    inner class ViewHolder(private val binding: ItemSoundForMixBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: SoundModel, onSelectedItem: (SoundModel) -> Unit) {
            binding.ivSound.loadImage(item.imageSound)
            binding.tvNameSound.text = binding.root.context.getString(item.nameSound)
            updateSelection(item.isSelected)

            itemView.setOnClickListener {
                if (!item.isSelected) {
                    onSelectedItem.invoke(item)
                }
            }
        }

        fun updateSelection(isSelected: Boolean) {
            binding.root.isActivated = isSelected
            binding.ivSelected.isVisible = isSelected
            binding.lottiePlaying.isVisible = isSelected
        }
    }
}