package com.iti.example.findpe2.models

import com.iti.example.findpe2.pojos.TimelineSlot
import com.iti.example.findpe2.pojos.Trip
import com.iti.example.findpe2.pojos.TripDuration
import com.iti.example.findpe2.pojos.UserTrip
import retrofit2.http.*

//BASE URL --> http://rsaber-001-site1.ftempurl.com/api/
interface TripsApiService {

    @GET("Trips")
    suspend fun getTrips(): List<Trip>

    @GET("Trips/Featuredlist/true")
    suspend fun getAllFeaturedTrips(): List<Trip>

    @GET("Trips/FillterByCategory/{category}")
    suspend fun getTripsByCategory(
        @Path("category") category: String,
        @Query("id") id: Int
    ): List<Trip>

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

    @GET("CalculateTrip")
    suspend fun getFilteredTrips(
        @Query("price") minPrice: Double,
        @Query("price") maxPrice: Double,
        @Query("place") fromPlace: String,
        @Query("place") toPlace: String,
        @Query("featured") wifi: Boolean,
        @Query("featured") restaurant: Boolean,
        @Query("featured") pool: Boolean,
        @Query("featured") inn: Boolean,
        @Query("featured") parking: Boolean,
        @Query("featured") isFeatured: Boolean,

        ): List<Trip>
    @POST("UserTrip/book")
    suspend fun likeBookOrSaveATrip(@Body userTrip: UserTrip):UserTrip

    @DELETE("UserTrip/{entity}")
    suspend fun deleteTripForUser(@Path("entity")entity:UserTrip)
}