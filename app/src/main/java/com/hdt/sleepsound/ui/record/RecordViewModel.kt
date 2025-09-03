package com.hdt.sleepsound.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hdt.sleepsound.data.local.mapper.RecordMapper
import com.hdt.sleepsound.data.model.RecordModel
import com.hdt.sleepsound.data.repository.RecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordViewModel(
    private val recordRepository: RecordRepository
): ViewModel() {

    private val _records = MutableStateFlow<List<RecordModel>>(emptyList())
    val records: StateFlow<List<RecordModel>> = _records.asStateFlow()

     fun insertRecord(record: RecordModel) {
        viewModelScope.launch {
            recordRepository.insertRecord(record)
        }
    }

    fun getAllRecords() {
        viewModelScope.launch {
            recordRepository.getAllRecords().collect {list ->
                _records.value = list.map {
                    RecordMapper.mapToModel(it)
                }
            }
        }
    }

}