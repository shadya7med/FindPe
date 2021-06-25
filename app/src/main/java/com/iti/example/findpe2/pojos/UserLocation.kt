package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLocation(
    val lat: Double,
    val lon: Double
) : Parcelable{
    constructor():this(0.0,0.0)
}
