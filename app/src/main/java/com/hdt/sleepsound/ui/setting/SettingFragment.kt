package com.hdt.sleepsound.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.databinding.FragmentSettingBinding
import com.hdt.sleepsound.utils.extensions.openUrl
import com.hdt.sleepsound.utils.extensions.popBackStack
import com.hdt.sleepsound.utils.extensions.shareApp
import com.hdt.sleepsound.utils.extensions.showDialog

class SettingFragment: BaseFragment<FragmentSettingBinding>() {

    companion object {
        private const val POLICY = "https://docs.google.com/document/d/e/2PACX-1vQnpsXaclusmzKsFkZ0ygceNKua9_VnfqgM6pivCYYxXBknteDyqPblURgAMxzkpsU7u-vunvCoIFA1/pub"
        private const val TERM = "https://docs.google.com/document/d/e/2PACX-1vTqj7nfI2r6o3xKHRgjtTH88djvvPH9XLCm0OrtFWK13oocobOxynsKM7Yue3nS5PxCMf1hC_ZgLXoW/pub"
    }

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {
        binding.btnBack.setOnClickListener { popBackStack() }
        binding.rlRate.isVisible = !preferenceHelper.isRated
        binding.rlRate.setOnClickListener {
            showDialog(RatingDialog {
                binding.rlRate.isVisible = !preferenceHelper.isRated
            })
        }
        binding.rlShare.setOnClickListener {
            shareApp()
        }
        binding.rlPolicy.setOnClickListener {
            openUrl(POLICY)
        }
        binding.rlTerm.setOnClickListener {
            openUrl(TERM)
        }

    }
}