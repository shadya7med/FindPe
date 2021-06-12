package com.iti.example.findpe2.models

import com.iti.example.findpe2.pojos.TimelineSlot
import com.iti.example.findpe2.pojos.Trip
import com.iti.example.findpe2.pojos.TripDuration
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//BASE URL --> http://rsaber-001-site1.ftempurl.com/api/
interface TripsApiService {

    @GET("Trips")
    suspend fun getTrips(): List<Trip>

    @GET("Trips/Featuredlist/true")
    suspend fun getAllFeaturedTrips(): List<Trip>

    @GET("Trips/FillterByCategory/{category}")
    suspend fun getTripsByCategory(@Path("category") category: String): List<Trip>

    @GET("TripDuration/GetTripDurations/{id}")
    suspend fun getTripDurations(@Path("id") id: Int): List<TripDuration>

    @GET("TimeLine/GetTimeLineSlots/{id}")
    suspend fun getTimelineSlot(@Path("id") id: Int): List<TimelineSlot>

    @GET("Trips/CityTo")
    suspend fun getAllToCitiesName(): List<String>

    @GET("Trips/CityFrom")
    suspend fun getAllFromCitiesName(): List<String>

    //(Double[] price, String[] place, Boolean[] featuredThinges)
    @GET("CalculateTrip")
    suspend fun getFeaturedFilteredTrips(
        @Query("price") prices: Array<Double>,
        @Query("place") places: Array<String>,
        @Query("featuredThinges") features: Array<Boolean>
    ): List<Trip>
}