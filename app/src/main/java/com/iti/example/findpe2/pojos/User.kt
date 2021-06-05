package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class AccountLevel{
    BRONZE,
    SILVER,
    GOLD
}

@Parcelize
data class User(
    val name:String,
    val title:String,
    val accountLevel:AccountLevel,
    val tripsHistory:ArrayList<String>,
    val workHistory:ArrayList<String>,
    val perks:ArrayList<String>,
    val languages:ArrayList<String>
):Parcelable
