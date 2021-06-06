package com.iti.example.findpe2.pojos

data class Companion(
    val id:String,
    val name:String,
    val imageUrl:String,
    var isLiked:Boolean,
    val accountLevel: AccountLevel,
    val expertLevel:String,
    val bio:String
)