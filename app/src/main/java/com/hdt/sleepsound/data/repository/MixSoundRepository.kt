package com.hdt.sleepsound.data.repository

import com.hdt.sleepsound.data.local.dao.MixSoundDao
import com.hdt.sleepsound.data.local.mapper.MixSoundMapper
import com.hdt.sleepsound.data.model.MixModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MixSoundRepository(
    private val dao: MixSoundDao
) {

    suspend fun insertMixSound(mixModel: MixModel) {
        withContext(Dispatchers.IO) {
            dao.insertMixSound(MixSoundMapper.mapToEntity(mixModel))
        }
    }

    fun getAllMixSound() = dao.getAllMixSound()
}