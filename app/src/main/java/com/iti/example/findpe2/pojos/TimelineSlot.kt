package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimelineSlot(
    val id: Int,
    val slotTitle: String,
    val slotTime: String,
    val slotDesc: String,
    val slotPhotoUrl: String,
): Parcelable