package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TripDuration(
    val id: Int,
    val durationStart: String,
    val durationEnd: String,
    val noOfSeets: Int,
    val tripId: Int,
) : Parcelable
