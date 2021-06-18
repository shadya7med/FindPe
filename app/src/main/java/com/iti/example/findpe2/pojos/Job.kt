package com.iti.example.findpe2.pojos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


enum class JobType (value:String){
    PERSONAL("personal"),
    BUSINESS("Business")
}

@Parcelize
data class Job(
    @SerializedName("JopId")
    val jobID:Int,
    val clientId:Int,
    @SerializedName("deacription")
    val description:String,
    @SerializedName("jopPrice")
    val jobPrice:Int,
    @SerializedName("jopDateStart")
    val jobDateStart:String,
    @SerializedName("jopDateEnd")
    val jobDateEnd:String,
    var isFinished:Boolean,
    val noOfBids:Int,
    @SerializedName("currenTBid")
    val currentBid:Int,
    @SerializedName("typeOfJop")
    val JobType:JobType,
    val companionID:String

):Parcelable
