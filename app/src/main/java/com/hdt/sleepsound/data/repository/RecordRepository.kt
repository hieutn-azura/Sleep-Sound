package com.hdt.sleepsound.data.repository

import com.hdt.sleepsound.data.local.dao.RecordDao
import com.hdt.sleepsound.data.local.mapper.RecordMapper
import com.hdt.sleepsound.data.model.RecordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecordRepository(
    private val dao: RecordDao
) {

    suspend fun insertRecord(record: RecordModel) {
        withContext(Dispatchers.IO) {
            dao.insertRecord(RecordMapper.mapToEntity(record))
        }
    }

    fun getAllRecords() = dao.getAllRecords()

}