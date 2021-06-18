package com.iti.example.findpe2.models

import com.iti.example.findpe2.constants.URLs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlacesApiModel {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())//MoshiConverterFactory.create(moshi))
        .baseUrl(URLs.PLACES_BASE_URL)//BASE_URL)
        .build()


    private val placesApi: PlacesApiService by lazy {
        PlacesApiModel.retrofit.create(PlacesApiService::class.java)
    }

    suspend fun getAllPlaces() = placesApi.getAllPlaces()

    suspend fun getPlace(xid: String) = placesApi.getPlace(xid)

}