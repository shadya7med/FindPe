package com.iti.example.findpe2.models

import com.iti.example.findpe2.constants.URLs.Companion.NOT_SECURE_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofitTripApi Singleton
object TripApi {

    //Create retrofit object using bulider and give it converter factory moshi
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())//MoshiConverterFactory.create(moshi))
        .baseUrl(NOT_SECURE_BASE_URL)//BASE_URL)
        .build()


    private val tripsApi : TripsApiService by lazy {
        retrofit.create(TripsApiService::class.java)
    }

    suspend fun getAllTrips() = tripsApi.getTrips()
    suspend fun getAllFeaturedTrips() = tripsApi.getAllFeaturedTrips()
}
