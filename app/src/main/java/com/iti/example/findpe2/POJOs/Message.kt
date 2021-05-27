package com.iti.example.findpe2.pojos

import java.util.*

data class Message(
    val msgBody: String?,
    val receiverID: String?,
    val senderID: String?,
    val senderEmail: String?,
    val senderName: String?,
    val time: Date?
) {
    //used by firebase for serialization
    constructor() : this(null, null, null, null, null, null) {

    }
}

/*messageBody
"waslak yabniiiii ?"
(string)
reciverID
"VNOvPJCJO7ZYYzSDFP4SZdgnAw42"
senderEmail
"yehiaazab01@gmail.com"
senderID
"a4JcBrwxPPRqiDaextZNAl9xUKi2"
senderName
"yehia azab"
time
May 23, 2021 at 10:36:11 PM UTC+2
* */