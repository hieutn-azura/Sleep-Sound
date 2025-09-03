package com.hdt.sleepsound.ui.dialog

import com.hdt.sleepsound.base.BaseDialogFragment
import com.hdt.sleepsound.databinding.DialogWarningMixBinding

class WarningSaveMixDialog : BaseDialogFragment<DialogWarningMixBinding>() {

    private var onExit: (() -> Unit)? = null

    override fun getViewBinding(): DialogWarningMixBinding {
        return DialogWarningMixBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.btnExit.setOnClickListener {
            onExit?.invoke()
            dismissAllowingStateLoss()
        }
        binding.btnCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    fun setListener(onExit: () -> Unit) {
        this.onExit = onExit
    }
}