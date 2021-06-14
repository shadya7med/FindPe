package com.iti.example.findpe2.models

import com.iti.example.findpe2.constants.URLs.Companion.NOT_SECURE_BASE_URL
import com.iti.example.findpe2.pojos.UserTrip
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofitTripApi Singleton
object TripApi {

    //Create retrofit object using bulider and give it converter factory moshi
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())//MoshiConverterFactory.create(moshi))
        .baseUrl(NOT_SECURE_BASE_URL)//BASE_URL)
        .build()


    private val tripsApi: TripsApiService by lazy {
        retrofit.create(TripsApiService::class.java)
    }

    suspend fun getAllTrips() = tripsApi.getTrips()
    suspend fun getAllFeaturedTrips() = tripsApi.getAllFeaturedTrips()
    suspend fun getTripsByCategory(category: String, id: Int) =
        tripsApi.getTripsByCategory(category, id)

    suspend fun getTimelineSlot(tripId: Int) = tripsApi.getTimelineSlot(tripId)
    suspend fun getTripDurations(tripId: Int) = tripsApi.getTripDurations(tripId)
    suspend fun getAllFromCities() = tripsApi.getAllFromCitiesName()
    suspend fun getAllToCities() = tripsApi.getAllToCitiesName()
    suspend fun getFeaturedFilteredTrips(
        prices: Array<Double>,
        places: Array<String>,
        features: Array<Boolean>
    ) = tripsApi.getFeaturedFilteredTrips(prices, places, features)

    suspend fun getFilteredTrips(
        minPrice: Double,
        maxPrice: Double,
        fromPlace: String,
        toPlace: String,
        wifi: Boolean,
        restaurant: Boolean,
        pool: Boolean,
        inn: Boolean,
        parking: Boolean,
        isFeatured: Boolean,
    ) = tripsApi.getFilteredTrips(
        minPrice, maxPrice, fromPlace, toPlace,
        wifi, restaurant, pool, inn, parking,
        isFeatured
    )
    suspend fun likeBookOrSaveATrip(userTrip:UserTrip) = tripsApi.likeBookOrSaveATrip(userTrip)

}
