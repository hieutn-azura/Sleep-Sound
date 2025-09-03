package com.hdt.sleepsound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hdt.sleepsound.data.local.entity.MixSoundEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MixSoundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMixSound(mixSoundEntity: MixSoundEntity)

    @Query("SELECT * FROM mix_sound")
    fun getAllMixSound(): Flow<MutableList<MixSoundEntity>>

}