package com.hdt.sleepsound.ui.mixsound

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import com.hdt.sleepsound.R
import com.hdt.sleepsound.base.BaseFragment
import com.hdt.sleepsound.data.model.MixModel
import com.hdt.sleepsound.data.model.MixSoundModel
import com.hdt.sleepsound.data.model.SoundModel
import com.hdt.sleepsound.databinding.FragmentMixSoundBinding
import com.hdt.sleepsound.ui.dialog.SaveRecordBottomSheetFragment
import com.hdt.sleepsound.ui.dialog.WarningSaveMixDialog
import com.hdt.sleepsound.ui.mysound.MySoundFragmentArgs
import com.hdt.sleepsound.utils.extensions.gone
import com.hdt.sleepsound.utils.extensions.invisible
import com.hdt.sleepsound.utils.extensions.navigate
import com.hdt.sleepsound.utils.extensions.popBackStack
import com.hdt.sleepsound.utils.extensions.resetSelection
import com.hdt.sleepsound.utils.extensions.showDialog
import com.hdt.sleepsound.utils.extensions.visible
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MixSoundFragment: BaseFragment<FragmentMixSoundBinding>() {

    private val adapter by lazy { MixSoundAdapter(::onItemClick) }
    private val selectedSounds = mutableListOf<Pair<SoundModel, MediaPlayer>>()
    private val viewModel: MixSoundViewModel by inject()
    private var volume1 = 100
    private var volume2 = 100
    private var volume3 = 100

    override fun isDisplayCutout(): Boolean = true

    override fun inflateBinding(inflater: LayoutInflater): FragmentMixSoundBinding {
        return FragmentMixSoundBinding.inflate(inflater)
    }

    override fun updateUI(view: View, savedInstanceState: Bundle?) {

        binding.rvSound.adapter = adapter
        adapter.submitList(
            MixSoundModel.listMixSound.resetSelection()
        )
    }

    private fun onItemClick(soundModel: SoundModel) {
        if (selectedSounds.size >= 3 || selectedSounds.any { it.first == soundModel }) return
        binding.cstMixSound.visible()

        val player = MediaPlayer.create(getContextF(), soundModel.sound).apply {
            isLooping = true
            setVolume(1f, 1f)
            start()
        }

        selectedSounds.add(soundModel to player)
        updateUIForSelectedSounds()
        adapter.toggleSelection(soundModel)
    }

    private fun updateUIForSelectedSounds() {
        binding.lnSound1.invisible()
        binding.lnSound2.invisible()
        binding.lnSound3.invisible()

        selectedSounds.forEachIndexed { index, (sound, _) ->
            when(index) {
                0 -> {
                    binding.lnSound1.visible()
                    binding.ivSound1.setImageResource(sound.imageSound)
                    binding.volumeSound1.progress = 100
                }
                1 -> {
                    binding.lnSound2.visible()
                    binding.ivSound2.setImageResource(sound.imageSound)
                    binding.volumeSound2.progress = 100
                }
                2 -> {
                    binding.lnSound3.visible()
                    binding.ivSound3.setImageResource(sound.imageSound)
                    binding.volumeSound3.progress = 100
                }
            }
        }

        if (selectedSounds.isEmpty()) binding.cstMixSound.gone()
    }

    private fun removeSoundAt(index: Int) {
        if (index < selectedSounds.size) {
            val (sound, player) = selectedSounds.removeAt(index)
            adapter.unselectSound(sound)
            player.release()
            updateUIForSelectedSounds()
        }
    }

    override fun initListener() {
        super.initListener()
        binding.btnBack.setOnClickListener {
            if (selectedSounds.isNotEmpty()) {
                showDialog(WarningSaveMixDialog().apply {
                    setListener { popBackStack() }
                })
            } else popBackStack()
        }

        binding.ivRemoveSound.setOnClickListener { removeSoundAt(0) }
        binding.ivRemoveSound2.setOnClickListener { removeSoundAt(1) }
        binding.ivRemoveSound3.setOnClickListener { removeSoundAt(2) }

        binding.volumeSound1.setOnSeekBarChangeListener(volumeListener(0))
        binding.volumeSound2.setOnSeekBarChangeListener(volumeListener(1))
        binding.volumeSound3.setOnSeekBarChangeListener(volumeListener(2))

        binding.btnSave.setOnClickListener {
            showDialog(SaveRecordBottomSheetFragment().apply {
                setListener {
                    lifecycleScope.launch {
                        viewModel.insertRecord(
                            MixModel(
                                id = System.currentTimeMillis().toInt(),
                                name = it,
                                uri1 = selectedSounds[0].first.sound,
                                uri2 = if (selectedSounds.size > 1) selectedSounds[1].first.sound else 0,
                                uri3 = if (selectedSounds.size > 2) selectedSounds[2].first.sound else 0,
                                volume1 = volume1,
                                volume2 = volume2,
                                volume3 = volume3
                            )
                        )

                        navigate(R.id.mySoundFragment, MySoundFragmentArgs(1).toBundle())
                    }
                }
            })
        }
    }

    private fun volumeListener(index: Int) = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser && index < selectedSounds.size) {
                val (_, player) = selectedSounds[index]
                val volume = progress / 100f
                player.setVolume(volume, volume)
                when(index){
                    0 -> volume1 = progress
                    1 -> volume2 = progress
                    2 -> volume3 = progress
                }
            }
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        selectedSounds.forEach { it.second.release() }
        selectedSounds.clear()
    }
}