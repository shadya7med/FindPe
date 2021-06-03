package com.iti.example.findpe2.models

import com.iti.example.findpe2.pojos.Trip
import retrofit2.http.GET

interface TripsApiService{

    @GET("Trips")
    suspend fun getTrips(): List<Trip>

    @GET("Trips/Featuredlist/true")
    suspend fun getAllFeaturedTrips():List<Trip>
}