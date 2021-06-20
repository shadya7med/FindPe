package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val id: Int,
    val name: String,
    val mobile: String,
    val phone: String,
    val address: String,
    val website: String,
    val email: String,
):Parcelable