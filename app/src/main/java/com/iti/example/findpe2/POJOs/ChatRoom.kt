package com.iti.example.findpe2.pojos


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class ChatRoom(
    val destinationUserID: String?,
    val destinationUsername: String?,
    val destinationUserImage: String?,//it should be String? currently we use a placeholder
    val chatLastMsgText: String?,
    val chatLastMsgTime: Date?
) : Parcelable {
    constructor() : this(null, null, null, null, null) {

    }
}
