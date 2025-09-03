package com.hdt.sleepsound.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey


@Keep
@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val uri: String
)