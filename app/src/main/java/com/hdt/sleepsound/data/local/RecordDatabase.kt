package com.hdt.sleepsound.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hdt.sleepsound.Constants
import com.hdt.sleepsound.data.local.dao.RecordDao
import com.hdt.sleepsound.data.local.entity.RecordEntity

@Database(
    version = 1,
    entities = [
        RecordEntity::class
    ],
    exportSchema = false
)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}

fun provideAppDb(context: Context) = Room.databaseBuilder(
    context, RecordDatabase::class.java, Constants.RECORD_DATABASE_NAME
).build()