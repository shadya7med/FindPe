package com.iti.example.findpe2.pojos

import java.util.*

data class ChatRoom(
    val destinationUserID:String?,
    val destinationUsername:String?,
    val destinationUserImage:Int?,//it should be String? currently we use a placeholder
    val chatLastMsgText:String?,
    val chatLastMsgTime:Date?
)
