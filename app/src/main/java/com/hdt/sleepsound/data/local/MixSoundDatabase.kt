package com.hdt.sleepsound.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hdt.sleepsound.Constants
import com.hdt.sleepsound.data.local.dao.MixSoundDao
import com.hdt.sleepsound.data.local.entity.MixSoundEntity

@Database(
    version = 1,
    entities = [
        MixSoundEntity::class
    ],
)
abstract class MixSoundDatabase : RoomDatabase() {
    abstract fun mixSoundDao(): MixSoundDao
}

fun provideAppDbMix(context: Context) = Room.databaseBuilder(
    context, MixSoundDatabase::class.java, Constants.MIX_SOUND_DATABASE_NAME
).build()