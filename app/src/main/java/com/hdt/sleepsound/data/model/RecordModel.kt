package com.hdt.sleepsound.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RecordModel (
    val id: Int,
    val name: String,
    val uri: String
): Parcelable