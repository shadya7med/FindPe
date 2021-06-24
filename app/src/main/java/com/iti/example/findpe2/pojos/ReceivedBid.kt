package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceivedBid (
    val companionID: String? = null,
    val clientID: String? = null,
    val companionImage: String? = null,
    val companionName: String? = null,
    val jobID: Long? = null,
    val proposal: String? = null,
    val offer: Long? = null,

): Parcelable