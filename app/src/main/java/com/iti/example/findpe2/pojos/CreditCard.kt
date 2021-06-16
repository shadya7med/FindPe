package com.iti.example.findpe2.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditCard(
    val creditCardID:Int,
    @SerializedName("cridetCardNUmber")
    val creditCardNumber:String,
    @SerializedName("exporyDate")
    val expiryDate:String,
    val userID:String
) :Parcelable