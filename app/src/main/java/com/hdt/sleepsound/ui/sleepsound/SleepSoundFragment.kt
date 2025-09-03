package com.hdt.sleepsound.ui.sleepsound

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.databinding.FragmentSleepSoundBinding
import com.hdt.sleepsound.data.model.CategoryModel
import com.hdt.sleepsound.utils.extensions.navigate
import com.hdt.sleepsound.utils.extensions.popBackStack

class SleepSoundFragment: BaseFragment<FragmentSleepSoundBinding>() {

    private val adapter by lazy { CategoryAdapter(::onCategoryClick) }

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentSleepSoundBinding {
        return FragmentSleepSoundBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = GridLayoutManager(getContextF(), 2)
        adapter.submitList(CategoryModel.entries)
    }

    override fun initListener() {
        super.initListener()

        binding.btnBack.setOnClickListener {
            popBackStack()
        }
    }

    private fun onCategoryClick(categoryModel: CategoryModel) {
        navigate(R.id.soundFragment, SoundFragmentArgs(categoryModel).toBundle())
    }
}