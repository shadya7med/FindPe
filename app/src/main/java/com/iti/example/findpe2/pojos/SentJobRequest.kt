package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SentJobRequest(
    val companionID: String? = null,
    val name: String? = null,
    val desc: String? = null,
    val offer: Long? = null,
    val tasks: List<String?>? = null,
    val statue: String? = null

):Parcelable
