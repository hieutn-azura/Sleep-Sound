package com.hdt.sleepsound.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "mix_sound")
data class MixSoundEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val uri1: Int,
    val uri2: Int,
    val uri3: Int,
    val volume1: Int,
    val volume2: Int,
    val volume3: Int
)