package com.iti.example.findpe2.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trip(
    val tripID: Int,
    val code: String,
    val priceBefore: Double,
    val priceAfter: Double,
    val discound: Double,
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
    val tripImages: List<TripImage>?,
    val users: List<User>?,
    val tripDurations: List<TripInfo>?,
    val company: String?,
    val hotel: Hotel?,
    val timelineSlots: TimelineSlot?,
    val tripCategory: String?,
    var isLiked:Boolean = false
) : Parcelable

//[
//{
//    "tripID": 1,
//    "code": "D3",
//    "priceBefore": 3500,
//    "priceAfter": 3150,
//    "discound": 0.1,
//    "name": "Dahab Fun Tour",
//    "origin": "Alexandria",
//    "destination": "Dahab",
//    "dateTimeStart": "2021-08-01T00:00:00",
//    "dateTimeEnd": "2021-08-08T00:00:00",
//    "duration": 7,
//    "tripDescription": "j d  dicjncjcd ddjcncjdjcdbcc dkncjdcjdcndjc   d cjncdjjdcnjcdjncjd",
//    "featured": true,
//    "noOfLikes": 120,
//    "noOfBook": 50,
//    "rateing": 4,
//    "isRemoved": false,
//    "hotelID": 2,
//    "companyID": 1,
//    "categoryID": 1,
//    "tripImages": [],
//    "tripDurations": null,
//    "company": null,
//    "hotel": {
//    "id": 2,
//    "name": "Hollywood dream",
//    "location": "USA",
//    "wifi": true,
//    "resurant": true,
//    "pool": true,
//    "bar": true,
//    "parkingSpot": true,
//    "trips": []
//},
//    "timelineSlots": null,
//    "tripCategory": null
//}
//]