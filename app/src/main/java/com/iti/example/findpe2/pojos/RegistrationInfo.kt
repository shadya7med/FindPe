package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegistrationInfo(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val phoneNumber: String,
    val country: String,
    val city: String

):Parcelable