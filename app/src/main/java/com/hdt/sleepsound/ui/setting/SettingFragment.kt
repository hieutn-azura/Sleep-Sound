package com.hdt.sleepsound.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.databinding.FragmentSettingBinding
import com.hdt.sleepsound.ui.dialog.RatingDialog
import com.hdt.sleepsound.utils.extensions.openUrl
import com.hdt.sleepsound.utils.extensions.popBackStack
import com.hdt.sleepsound.utils.extensions.shareApp
import com.hdt.sleepsound.utils.extensions.showDialog

class SettingFragment: BaseFragment<FragmentSettingBinding>() {

    companion object {
        private const val POLICY = "https://docs.google.com/document/d/e/2PACX-1vSDu_TJaGINl6Fg0CWTO1zV59oGbNnXaHNNGBFtm5vpZAD9l3zzTkX2Isnu-H-JZg/pub"
        private const val TERM = "https://docs.google.com/document/d/e/2PACX-1vRqTS9Gb7i9A5Rg7pIOlBJMR84iCfPheyAxIjBM7-q4Jx-8fTsH5n0u-P5bAPqUyQ/pub"
    }

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {
        binding.btnBack.setOnClickListener { popBackStack() }
        binding.rlRate.isVisible = !preferenceHelper.isRated
        binding.rlRate.setOnClickListener {
            showDialog(RatingDialog().apply {
                setListener {
                    updateRate()
                }

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

    private fun updateRate() {
        binding.rlRate.isVisible = !preferenceHelper.isRated
    }
}