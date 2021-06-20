package com.iti.example.findpe2.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

enum class AccountLevel(val value:String) {
    BRONZE("bronze"),
    SILVER("Silver"),
    GOLD("gold")
}

@Parcelize
data class User(
    val userID: String,
    @SerializedName("userName")
    val name: String,
    @SerializedName("passsword")
    val password: String? = null,
    val email: String,
    val imageUrl: String,
    val isVerified: Boolean,
    val balance: Int,
    val profileImageUrl: String? = null,
    val discreminator: String,
    val creditCards: CreditCard? = null
) : Parcelable
