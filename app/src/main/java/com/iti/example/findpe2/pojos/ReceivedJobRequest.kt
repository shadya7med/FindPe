package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


enum class RequestStatus(val value: String){
    WAITING("Waiting"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected")
}
@Parcelize
data class ReceivedJobRequest(
    val clientID: String? = null,
    val clientName: String? = null,
    val clientImage: String? = null,
    val desc: String? = null,
    val offer: Long? = null,
    val tasks: List<String?>? = null,
):Parcelable