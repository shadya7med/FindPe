package com.iti.example.findpe2.models

import com.iti.example.findpe2.pojos.TimelineSlot
import com.iti.example.findpe2.pojos.Trip
import com.iti.example.findpe2.pojos.TripDuration
import retrofit2.http.GET
import retrofit2.http.Path

interface TripsApiService{

    @GET("Trips")
    suspend fun getTrips(): List<Trip>

    @GET("Trips/Featuredlist/true")
    suspend fun getAllFeaturedTrips():List<Trip>

    @GET("Trips/FillterByCategory/{category}")
    suspend fun getTripsByCategory(@Path("category") category: String): List<Trip>

    @GET("TripDuration/GetTripDurations/{id}")
    suspend fun getTripDurations(@Path("id") id: Int): List<TripDuration>

    @GET("TimeLine/GetTimeLineSlots/{id}")
    suspend fun getTimelineSlot(@Path("id") id: Int): List<TimelineSlot>
}