package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trip(
    val id: Int,
    val code: String,
    val price: Int,
    val name: String,
    val origin: String,
    val destination: String,
    val dateTimeStart: String,
    val dateTimeEnd: String,
    val duration: Int,
    val tripDescription: String,
    val category: String,
    val featured: Boolean,
    val hotelID: String,
    val companyID: Int,
    val tripImages: List<String>?,
    val users: List<User>?,
    val tripDurations: List<TripInfo>?,
    val company: String?,
    val hotel: String?
) : Parcelable

//"id": 7,
//"code": "D3",
//"price": 3500,
//"name": "Explore&Relax",
//"origin": "Alexandria",
//"destination": "Dahab",
//"dateTimeStart": "2021-08-07T00:00:00",
//"dateTimeEnd": "2021-08-14T00:00:00",
//"duration": 7,
//"tripDescription": "Sea and Mountains",
//"category": "Relax",
//"featured": true,
//"hotelID": 5,
//"companyID": 2,
//"tripImages": null,
//"users": null,
//"tripDurations": null,
//"company": null,
//"hotel": null