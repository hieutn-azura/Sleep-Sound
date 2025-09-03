package com.hdt.sleepsound.ui.sleepsound

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.databinding.FragmentSoundBinding
import com.hdt.sleepsound.data.model.SoundModel
import com.hdt.sleepsound.ui.dialog.SetTimerDialog
import com.hdt.sleepsound.utils.extensions.gone
import com.hdt.sleepsound.utils.extensions.popBackStack
import com.hdt.sleepsound.utils.extensions.showDialog
import com.hdt.sleepsound.utils.extensions.visible
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SoundFragment: BaseFragment<FragmentSoundBinding>() {

    private val navArg by navArgs<SoundFragmentArgs>()
    private val categoryModel by lazy { navArg.categoryModel }

    private val adapter by lazy { SoundAdapter(::onSoundClick) }

    private var countDownTimeJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentSoundBinding {
        return FragmentSoundBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {
        binding.tvTitle.text = getContextF().getString(categoryModel.nameCategory)
        binding.rvSound.adapter = adapter
        binding.rvSound.layoutManager = GridLayoutManager(getContextF(), 3)
        adapter.submitList(
            SoundModel.listSound.filter { it.categoryModel == categoryModel }.map { it.copy() }
        )
    }

    override fun initListener() {
        super.initListener()

        binding.btnBack.setOnClickListener {
            popBackStack()
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

    private fun onSoundClick(soundModel: SoundModel) {
        binding.cstPlaySound.visible()
        binding.ivPlay.isActivated = true
        binding.tvNameSound.text = getContextF().getString(soundModel.nameSound)

        adapter.selectItem(soundModel)

        playSound(soundModel)
    }

    private fun playSound(soundModel: SoundModel) {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaPlayer = MediaPlayer.create(getContextF(), soundModel.sound)
        mediaPlayer?.isLooping = true
        mediaPlayer?.setVolume(1.0f, 1.0f)
        mediaPlayer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
        countDownTimeJob?.cancel()
    }
}