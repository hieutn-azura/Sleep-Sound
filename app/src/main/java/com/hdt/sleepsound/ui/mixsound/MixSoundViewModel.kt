package com.hdt.sleepsound.ui.mixsound

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdt.sleepsound.data.local.mapper.MixSoundMapper
import com.hdt.sleepsound.data.model.MixModel
import com.hdt.sleepsound.data.repository.MixSoundRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MixSoundViewModel(
    private val repository: MixSoundRepository
): ViewModel() {

    private val _mixSounds = MutableStateFlow<List<MixModel>>(emptyList())
    val mixSounds: StateFlow<List<MixModel>> = _mixSounds.asStateFlow()

    fun insertRecord(mixModel: MixModel) {
        viewModelScope.launch {
            repository.insertMixSound(mixModel)
        }
    }

    fun getAllMixSound() {
        viewModelScope.launch {
            repository.getAllMixSound().collect {list ->
                _mixSounds.value = list.map {
                    MixSoundMapper.mapToModel(it)
                }
            }
        }
    }
}