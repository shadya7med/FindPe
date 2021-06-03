package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val contacted:Boolean?,//dumb
    val name:String?,
):Parcelable
