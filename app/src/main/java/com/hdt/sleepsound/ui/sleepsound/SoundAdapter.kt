package com.hdt.sleepsound.ui.sleepsound

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hdt.sleepsound.databinding.ItemSoundBinding
import com.hdt.sleepsound.data.model.SoundModel
import com.hdt.sleepsound.utils.extensions.loadImage

class SoundAdapter(
    private val onClickItem: (SoundModel) -> Unit
) : ListAdapter<SoundModel, SoundAdapter.ViewHolder>(
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
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).imageSound.hashCode().toLong()
    }

    companion object {
        private const val PAYLOAD_SELECTION = "PAYLOAD_SELECTION"
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any?>) {
        if (payloads.isEmpty()) {
            holder.bindView(getItem(position), onClickItem)
        } else {
            payloads.forEach {
                if (it == PAYLOAD_SELECTION) {
                    holder.updateSelection(getItem(position).isSelected)
                }
            }
        }
    }

    fun toggleSelection(soundModel: SoundModel) {
        val index = currentList.indexOf(soundModel)
        if (index != -1) {
            soundModel.isSelected = !soundModel.isSelected
            notifyItemChanged(index, PAYLOAD_SELECTION)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position), onClickItem)
    }

    fun getSelectedCount(): Int {
        return currentList.count { it.isSelected }
    }

    fun unselectItem(soundModel: SoundModel) {
        currentList.map {
            if (it == soundModel) it.isSelected = false
        }
        notifyItemChanged(currentList.indexOf(soundModel))
    }

    fun selectItem(newItem: SoundModel) {
        val oldIndex = currentList.indexOfFirst { it.isSelected }
        val newIndex = currentList.indexOfFirst { it.imageSound == newItem.imageSound }

        if (oldIndex == newIndex) return

        val newList = currentList.toMutableList()

        if (oldIndex != -1) {
            val oldItem = newList[oldIndex].copy(isSelected = false)
            newList[oldIndex] = oldItem
        }

        if (newIndex != -1) {
            val updatedNewItem = newList[newIndex].copy(isSelected = true)
            newList[newIndex] = updatedNewItem
        }

        submitList(newList)
    }

    inner class ViewHolder(private val binding: ItemSoundBinding) :
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

        fun updateSelection(isSelected: Boolean) {
            binding.root.isActivated = isSelected
            binding.ivSelected.isVisible = isSelected
            binding.lottiePlaying.isVisible = isSelected
        }
    }
}