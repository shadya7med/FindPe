package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class TripOpState(val value: String) {
    LIKE("like"),
    BOOK("book"),
    SAVE("save")
}

@Parcelize
data class UserTrip(
    val userID: String,
    val user: User? = null,
    val tripID: Int,
    val trip: Trip? = null,
    val payment: Double? = 0.0,
    val likeORBookORSave: String
) : Parcelable
