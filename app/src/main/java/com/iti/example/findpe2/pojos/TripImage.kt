package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TripImage(
    val id: Int,
    val image: String,
    val tripId: Int
): Parcelable
//    "id": 1,
//    "image": "Image\\52220EB2-31C9-44C1-B01A-987E291CC2CD.jpeg",
//    "tripId": 1