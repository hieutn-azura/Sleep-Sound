package com.hdt.sleepsound.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MixModel (
    val id: Int,
    val name: String,
    val uri1: Int,
    val uri2: Int,
    val uri3: Int,
    val volume1: Int,
    val volume2: Int,
    val volume3: Int
): Parcelable