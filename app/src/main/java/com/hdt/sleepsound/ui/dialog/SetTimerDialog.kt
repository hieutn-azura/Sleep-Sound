package com.hdt.sleepsound.ui.dialog

import com.hdt.sleepsound.base.BaseDialogFragment
import com.hdt.sleepsound.databinding.DialogSetTimerBinding

class SetTimerDialog: BaseDialogFragment<DialogSetTimerBinding>() {

    private var onSetTimer: ((hour: Int, minute: Int) -> Unit)? = null

    override fun getViewBinding(): DialogSetTimerBinding {
        return DialogSetTimerBinding.inflate(layoutInflater)
    }

    override fun initView() {

        binding.btnSave.setOnClickListener {
            val hour = binding.edtHour.text.toString().toIntOrNull() ?: 0
            val minute = binding.edtMinute.text.toString().toIntOrNull() ?: 0
            onSetTimer?.invoke(hour, minute)
            dismiss()
        }
    }

    fun setListener(onSetTimer: (hour: Int, minute: Int) -> Unit) {
        this.onSetTimer = onSetTimer
    }
}