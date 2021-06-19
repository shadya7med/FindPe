package com.iti.example.findpe2.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hotel(
    val id: Int,
    val name: String,
    val location: String,
    val wifi: Boolean,
    @SerializedName("resurant")
    val restaurant: Boolean,
    val pool: Boolean,
    val bar: Boolean,
    val parkingSpot: Boolean
) : Parcelable
