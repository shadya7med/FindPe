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
    val featured: Boolean,
    val noOfLikes: Int,
    val noOfBook: Int,
    val rateing: Int,
    val hotelID: Int,
    val companyID: Int,
    val categoryId: Int,
    val tripImages: List<String>?,
    val users: List<User>?,
    val tripDurations: List<TripInfo>?,
    val company: String?,
    val hotel: String?,
    val timelineSlots: TimelineSlot?,
    val tripCategory: String?
) : Parcelable

//{
//    "id": 2,
//    "code": "D3",
//    "price": 3000,
//    "name": "Dahab Fun Tour",
//    "origin": "Alexandria",
//    "destination": "Dahab",
//    "dateTimeStart": "2021-08-01T00:00:00",
//    "dateTimeEnd": "2021-08-08T00:00:00",
//    "duration": 7,
//    "tripDescription": "have fun in dahab",
//    "featured": true,
//    "noOfLikes": 119,
//    "noOfBook": 50,
//    "rateing": 4,
//    "hotelID": 6,
//    "companyID": 1,
//    "categoryID": 3,
//    "tripImages": null,
//    "users": null,
//    "tripDurations": null,
//    "company": null,
//    "hotel": null,
//    "timelineSlots": null,
//    "tripCategory": null
//},