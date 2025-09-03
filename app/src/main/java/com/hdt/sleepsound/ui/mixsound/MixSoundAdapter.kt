package com.hdt.sleepsound.ui.mixsound

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hdt.sleepsound.data.model.MixSoundModel
import com.hdt.sleepsound.data.model.SoundModel
import com.hdt.sleepsound.databinding.ItemCategoryMixSoundBinding
import com.hdt.sleepsound.databinding.ItemMixSoundBinding

class MixSoundAdapter(
    private val onSoundClick: (SoundModel) -> Unit
) : ListAdapter<MixSoundModel, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        private const val TYPE_TEXT = 0
        private const val TYPE_CATEGORY = 1
        private const val PAYLOAD_UPDATE_SOUNDS = "payload_update_sounds"
    }

    private val sharedPool = RecyclerView.RecycledViewPool()
    private var currentMixList: List<MixSoundModel> = emptyList()

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MixSoundModel.TextItem -> TYPE_TEXT
            is MixSoundModel.CategoryItem -> TYPE_CATEGORY
        }
    }

    override fun getItemId(position: Int): Long {
        return when (val item = getItem(position)) {
            is MixSoundModel.TextItem -> item.text.toLong()
            is MixSoundModel.CategoryItem -> item.categoryId.toLong()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXT -> {
                val binding = ItemCategoryMixSoundBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TextViewHolder(binding)
            }

            TYPE_CATEGORY -> {
                val binding = ItemMixSoundBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CategoryViewHolder(binding, sharedPool, onSoundClick)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MixSoundModel.TextItem -> (holder as TextViewHolder).bind(item)
            is MixSoundModel.CategoryItem -> (holder as CategoryViewHolder).bind(item)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (holder is CategoryViewHolder && payloads.contains(PAYLOAD_UPDATE_SOUNDS)) {
                val item = getItem(position) as MixSoundModel.CategoryItem
                holder.updateSoundList(item.items)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun submitList(list: List<MixSoundModel>?) {
        super.submitList(list)
        currentMixList = list ?: emptyList()
    }

    /** Public API để Fragment gọi **/

    fun resetAllSelections() {
        val newList = currentMixList.map { item ->
            when (item) {
                is MixSoundModel.TextItem -> item
                is MixSoundModel.CategoryItem ->
                    item.copy(items = item.items.map { it.copy(isSelected = false) })
            }
        }
        submitList(newList)
    }

    fun unselectSound(soundModel: SoundModel) {
        val newList = currentMixList.map { item ->
            when (item) {
                is MixSoundModel.TextItem -> item
                is MixSoundModel.CategoryItem ->
                    item.copy(items = item.items.map {
                        if (it.nameSound == soundModel.nameSound) it.copy(isSelected = false) else it
                    })
            }
        }
        submitList(newList)
    }

    fun toggleSelection(soundModel: SoundModel) {
        val newList = currentMixList.map { item ->
            when (item) {
                is MixSoundModel.TextItem -> item
                is MixSoundModel.CategoryItem ->
                    item.copy(items = item.items.map {
                        if (it.nameSound == soundModel.nameSound) it.copy(isSelected = true) else it
                    })
            }
        }
        submitList(newList)
    }

    fun getSelectedCount(): Int {
        return currentMixList.filterIsInstance<MixSoundModel.CategoryItem>()
            .sumOf { it.items.count { sound -> sound.isSelected } }
    }

    /** ViewHolder classes **/

    class TextViewHolder(private val binding: ItemCategoryMixSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MixSoundModel.TextItem) {
            binding.tvNameCategory.text = binding.root.context.getString(item.text)
        }
    }

    class CategoryViewHolder(
        private val binding: ItemMixSoundBinding,
        private val sharedPool: RecyclerView.RecycledViewPool,
        private val onSoundClick: (SoundModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val soundAdapter by lazy {
            SoundForMixAdapter(onSoundClick).apply {
                setHasStableIds(true)
            }
        }

        init {
            binding.rvSound.layoutManager = LinearLayoutManager(
                binding.root.context, RecyclerView.HORIZONTAL, false
            )
            binding.rvSound.setRecycledViewPool(sharedPool)
            binding.rvSound.setHasFixedSize(true)
            binding.rvSound.adapter = soundAdapter
        }

        fun bind(category: MixSoundModel.CategoryItem) {
            soundAdapter.submitList(category.items)
        }

        fun updateSoundList(soundList: List<SoundModel>) {
            soundAdapter.submitList(soundList)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MixSoundModel>() {
        override fun areItemsTheSame(oldItem: MixSoundModel, newItem: MixSoundModel): Boolean {
            return when {
                oldItem is MixSoundModel.TextItem && newItem is MixSoundModel.TextItem ->
                    oldItem.text == newItem.text
                oldItem is MixSoundModel.CategoryItem && newItem is MixSoundModel.CategoryItem ->
                    oldItem.categoryId == newItem.categoryId
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: MixSoundModel, newItem: MixSoundModel): Boolean {
            // So sánh nội dung bao gồm cả danh sách items bên trong
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: MixSoundModel, newItem: MixSoundModel): Any? {
            return if (oldItem is MixSoundModel.CategoryItem && newItem is MixSoundModel.CategoryItem) {
                if (oldItem.items != newItem.items) PAYLOAD_UPDATE_SOUNDS else null
            } else {
                null
            }
        }
    }
}