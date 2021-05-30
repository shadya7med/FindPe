package com.iti.example.findpe2.models

import com.iti.example.findpe2.pojos.Trip
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

//Base url for api
private const val BASE_URL = "https://trips-final.herokuapp.com/api/"

//Create moshi object using moshi builder to parse Json into Trip
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Create retrofit object using bulider and give it converter factory moshi
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


//Trip service
interface TripApiService{
    @GET("Trips")
    suspend fun getTrips(): List<Trip>
}

//retrofitTripApi Singleton
object TripApi {
    val retrofitTripService : TripApiService by lazy {
        retrofit.create(TripApiService::class.java)
    }
}
