package com.hdt.sleepsound.ui.mysound

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.data.model.MixModel
import com.hdt.sleepsound.databinding.FragmentMixSaveBinding
import com.hdt.sleepsound.ui.dialog.SetTimerDialog
import com.hdt.sleepsound.ui.mixsound.MixSoundViewModel
import com.hdt.sleepsound.utils.extensions.gone
import com.hdt.sleepsound.utils.extensions.showDialog
import com.hdt.sleepsound.utils.extensions.visible
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MixSaveFragment: BaseFragment<FragmentMixSaveBinding>() {

    private val adapter by lazy { MixSaveAdapter(::onMixClick) }
    private var countDownTimeJob: Job? = null
    private var mediaPlayer1: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null
    private var mediaPlayer3: MediaPlayer? = null
    private val viewModel: MixSoundViewModel by inject()

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentMixSaveBinding {
        return FragmentMixSaveBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllMixSound()
        binding.rvMix.adapter = adapter
    }

    override fun initObserver() {
        super.initObserver()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mixSounds.collect {
                binding.rvMix.isVisible = it.isNotEmpty()
                binding.cstEmpty.isVisible = it.isEmpty()
                adapter.submitList(it)
            }
        }

        binding.ivPlay.setOnClickListener {
            if (binding.ivPlay.isActivated) {
                stopCountDownTime()
            } else {
                mediaPlayer1?.start()
                mediaPlayer2?.start()
                mediaPlayer3?.start()
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
        mediaPlayer1?.pause()
        mediaPlayer2?.pause()
        mediaPlayer3?.pause()
        binding.ivPlay.isActivated = false
    }

    fun formatTime(totalSeconds: Int): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return getContextF().getString(R.string.format_timer, hours, minutes, seconds)
    }

    private fun playSound(mixModel: MixModel) {
        if (mixModel.uri1 != 0) {
            mediaPlayer1?.release()
            mediaPlayer1 = null
            mediaPlayer1 = MediaPlayer.create(getContextF(), mixModel.uri1)
            mediaPlayer1?.isLooping = true
            mediaPlayer1?.setVolume(mixModel.volume1/100f, mixModel.volume1/100f)
            mediaPlayer1?.start()
        }

        if (mixModel.uri2 != 0) {
            mediaPlayer2?.release()
            mediaPlayer2 = null
            mediaPlayer2 = MediaPlayer.create(getContextF(), mixModel.uri2)
            mediaPlayer2?.isLooping = true
            mediaPlayer2?.setVolume(mixModel.volume2/100f, mixModel.volume2/100f)
            mediaPlayer2?.start()
        }

        if (mixModel.uri3 != 0) {
            mediaPlayer3?.release()
            mediaPlayer3 = null
            mediaPlayer3 = MediaPlayer.create(getContextF(), mixModel.uri3)
            mediaPlayer3?.isLooping = true
            mediaPlayer3?.setVolume(mixModel.volume3/100f, mixModel.volume3/100f)
            mediaPlayer3?.start()
        }
    }

    private fun onMixClick(mixModel: MixModel) {
        binding.cstPlaySound.visible()
        binding.ivPlay.isActivated = true
        binding.tvNameSound.text = mixModel.name
        mediaPlayer1?.release()
        mediaPlayer1 = null
        mediaPlayer2?.release()
        mediaPlayer2 = null
        mediaPlayer3?.release()
        mediaPlayer3 = null
        playSound(mixModel)
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer1?.release()
        mediaPlayer1 = null
        mediaPlayer2?.release()
        mediaPlayer2 = null
        mediaPlayer3?.release()
        mediaPlayer3 = null
        countDownTimeJob?.cancel()
        binding.cstPlaySound.gone()
    }
}