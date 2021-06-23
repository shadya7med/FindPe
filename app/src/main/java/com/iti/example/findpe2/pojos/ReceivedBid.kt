package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceivedBid (
    val companionID: String? = null,
    val clientID: String? = null,
    val name: String? = null,
    val proposal: String? = null,
    val offer: Long? = null,
    val status: String? = null

): Parcelable