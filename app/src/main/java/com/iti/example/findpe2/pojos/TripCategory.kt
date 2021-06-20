package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TripCategory(
    val categoryID: Int,
    val categoryName: String,
    val categoryPhotoUrl: String,

    ) : Parcelable
