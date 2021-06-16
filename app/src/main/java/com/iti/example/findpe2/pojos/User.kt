package com.iti.example.findpe2.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

enum class AccountLevel {
    BRONZE,
    SILVER,
    GOLD
}

@Parcelize
data class User(
    val userID: String,
    @SerializedName("userName")
    val name: String,
    @SerializedName("passsword")
    val password: String,
    val email: String,
    val imageUrl: String,
    val isVerified: Boolean,
    val balance: Int,
    val profileImageUrl: String,
    val discreminator: String,
    val creditCards: CreditCard? = null
) : Parcelable
