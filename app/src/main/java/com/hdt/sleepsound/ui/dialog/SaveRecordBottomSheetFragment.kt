package com.hdt.sleepsound.ui.dialog

import com.hdt.sleepsound.base.BaseBottomSheetFragment
import com.hdt.sleepsound.databinding.BottomSheetFragmentSaveRecordBinding

class SaveRecordBottomSheetFragment: BaseBottomSheetFragment<BottomSheetFragmentSaveRecordBinding>() {

    private var onSaveRecord: ((nameFile: String) -> Unit)? = null

    override fun getViewBinding(): BottomSheetFragmentSaveRecordBinding {
        return BottomSheetFragmentSaveRecordBinding.inflate(layoutInflater)
    }

    override fun initView() {

        binding.btnCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnSave.setOnClickListener {
            if (binding.edtNameFile.text.toString().trim().isEmpty()) return@setOnClickListener
            onSaveRecord?.invoke(binding.edtNameFile.text.toString())
            dismissAllowingStateLoss()
        }
    }

    fun setListener(onSaveRecord: (nameFile: String) -> Unit) {
        this.onSaveRecord = onSaveRecord
    }
}