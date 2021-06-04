package com.iti.example.findpe2.models

import com.iti.example.findpe2.constants.URLs.Companion.BASE_URL
import com.iti.example.findpe2.constants.URLs.Companion.NOT_SECURE_BASAE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//retrofitTripApi Singleton
object TripApi {

    //Create moshi object using moshi builder to parse Json into Trip
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //Create retrofit object using bulider and give it converter factory moshi
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)//BASE_URL)
        .build()


    private val tripsApi : TripsApiService by lazy {
        retrofit.create(TripsApiService::class.java)
    }

    suspend fun getAllTrips() = tripsApi.getTrips()
    suspend fun getAllFeaturedTrips() = tripsApi.getAllFeaturedTrips()
}
