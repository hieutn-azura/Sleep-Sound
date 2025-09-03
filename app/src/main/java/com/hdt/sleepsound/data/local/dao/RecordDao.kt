package com.hdt.sleepsound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hdt.sleepsound.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: RecordEntity)

    @Query("SELECT * FROM records")
    fun getAllRecords(): Flow<MutableList<RecordEntity>>

}