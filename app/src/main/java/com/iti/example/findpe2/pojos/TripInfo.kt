package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripInfo(
    val tripId:Int,
    val numOfSeats:Int,
    val fromDate:String,
    val toDate:String
):Parcelable