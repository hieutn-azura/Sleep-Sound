package com.hdt.sleepsound.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.databinding.FragmentHomeBinding
import com.hdt.sleepsound.utils.extensions.gone
import com.hdt.sleepsound.utils.extensions.navigate
import com.hdt.sleepsound.utils.extensions.openSettingApplication
import com.hdt.sleepsound.utils.extensions.visible

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val requestRecordRadioPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissions ->
        if (!permissions) {
            val showRationale =
                shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)
            if (!showRationale) {
                openSettingApplication(getContextF())
            }
        }
    }

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {

        binding.btnSleepSound.setOnClickListener {
            navigate(R.id.sleepSoundFragment)
        }

        binding.btnRecord.setOnClickListener {
            if (!checkRecordRadioPermissions()) {
                requestRecordRadioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            } else {
                navigate(R.id.recordFragment)
            }
        }

        binding.btnMySound.setOnClickListener {
            navigate(R.id.mySoundFragment)
        }

        binding.btnMixSound.setOnClickListener {
            binding.flLoading.visible()
            navigate(R.id.mixSoundFragment)
        }

        binding.btnSetting.setOnClickListener {
            navigate(R.id.settingFragment)
        }
    }

    private fun checkRecordRadioPermissions(): Boolean {
        val readStoragePermission = ContextCompat.checkSelfPermission(
            getContextF(),
            Manifest.permission.RECORD_AUDIO
        )
        return readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        binding.flLoading.gone()
    }
}