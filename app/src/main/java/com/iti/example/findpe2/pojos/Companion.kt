package com.iti.example.findpe2.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


enum class ExpertLevel(val value:String){
    Amateur("Amateur"),
    PROFESSIONAL("Professional")
}

@Parcelize
data class Companion(
    @SerializedName("companinID")
    val companionID: String,
    @SerializedName("title")
    val expertLevel:String,
    val city:String,
    val country:String,
    val nationality:String,
    val criminalRecordUrl:String,
    var isLiked:Boolean,
    val badge: String,
    val idCardImageUrl:String,
    val censorshipImageUrl:String,
    val rating:Int,
    @SerializedName("jop")
    val job:Job? = null,
    @SerializedName("user")
    val userInfo: User? = null,
) : Parcelable
