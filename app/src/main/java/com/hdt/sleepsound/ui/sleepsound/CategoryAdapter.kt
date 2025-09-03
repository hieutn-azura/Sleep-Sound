package com.hdt.sleepsound.ui.sleepsound

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hdt.sleepsound.databinding.ItemCategoryBinding
import com.hdt.sleepsound.data.model.CategoryModel

class CategoryAdapter(
    private val onClickItem: (CategoryModel) -> Unit,
) : ListAdapter<CategoryModel, CategoryAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.imageCategory == newItem.imageCategory
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position), onClickItem)
    }

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: CategoryModel, onSelectedItem: (CategoryModel) -> Unit) {
            binding.ivCategory.setImageResource(item.imageCategory)
            binding.tvCategory.text = binding.root.context.getString(item.nameCategory)
            itemView.setOnClickListener {
                onSelectedItem(item)
            }
        }
    }
}