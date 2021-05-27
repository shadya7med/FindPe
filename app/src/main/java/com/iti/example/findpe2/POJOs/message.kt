package com.iti.example.findpe2.pojos

data class Message(
    val text:String?,
    val name:String?,
    val photoUrl:String?,
    val imageUrl:String?
){
    constructor() : this(null,null,null,null) {

    }
}
