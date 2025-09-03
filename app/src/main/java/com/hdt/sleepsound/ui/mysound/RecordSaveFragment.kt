package com.hdt.sleepsound.ui.mysound

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.data.model.RecordModel
import com.hdt.sleepsound.databinding.FragmentRecordSaveBinding
import com.hdt.sleepsound.ui.dialog.SetTimerDialog
import com.hdt.sleepsound.ui.record.RecordViewModel
import com.hdt.sleepsound.utils.extensions.gone
import com.hdt.sleepsound.utils.extensions.showDialog
import com.hdt.sleepsound.utils.extensions.visible
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RecordSaveFragment: BaseFragment<FragmentRecordSaveBinding>() {

    private val adapter by lazy { RecordAdapter(::onRecordClick) }
    private var countDownTimeJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null
    private val viewModel: RecordViewModel by inject()

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentRecordSaveBinding {
        return FragmentRecordSaveBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllRecords()
        binding.rvRecord.adapter = adapter
    }

    override fun initObserver() {
        super.initObserver()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.records.collect {
                binding.rvRecord.isVisible = it.isNotEmpty()
                binding.cstEmpty.isVisible = it.isEmpty()
                adapter.submitList(it)
            }
        }

        binding.ivPlay.setOnClickListener {
            if (binding.ivPlay.isActivated) {
                stopCountDownTime()
            } else {
                mediaPlayer?.start()
                binding.ivPlay.isActivated = true
            }
        }

        binding.ivTimer.setOnClickListener {
            showDialog(SetTimerDialog().apply {
                setListener { hour, minute ->
                    if (hour != 0 || minute != 0) {
                        startCountDownTime(hour, minute)
                    }
                }
            })
        }
    }

    private fun startCountDownTime(hour: Int = 0, minute: Int = 0) {
        binding.tvDuration.visible()
        binding.ivTimer.gone()
        countDownTimeJob?.cancel()
        val totalSeconds = hour * 3600 + minute * 60
        countDownTimeJob = lifecycleScope.launch {
            for (time in totalSeconds downTo 1) {
                binding.tvDuration.text = formatTime(time)
                delay(1000)
            }
            stopCountDownTime()
        }
    }

    private fun stopCountDownTime() {
        countDownTimeJob?.cancel()
        binding.tvDuration.gone()
        binding.ivTimer.visible()
        mediaPlayer?.pause()
        binding.ivPlay.isActivated = false
    }

    fun formatTime(totalSeconds: Int): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return getContextF().getString(R.string.format_timer, hours, minutes, seconds)
    }

    private fun onRecordClick(item: RecordModel) {
        binding.cstPlaySound.visible()
        binding.ivPlay.isActivated = true
        binding.tvNameSound.text = item.name

        playSound(item)
    }

    private fun playSound(recordModel: RecordModel) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(getContextF(), recordModel.uri.toUri())
        mediaPlayer?.isLooping = true
        mediaPlayer?.setVolume(1.0f, 1.0f)
        mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.release()
        mediaPlayer = null
        countDownTimeJob?.cancel()
        binding.cstPlaySound.gone()
    }
}