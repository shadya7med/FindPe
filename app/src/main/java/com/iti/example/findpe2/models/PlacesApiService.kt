package com.iti.example.findpe2.models

import com.iti.example.findpe2.constants.Constants.Companion.API_KEY
import com.iti.example.findpe2.pojos.PlaceToVisit
import com.iti.example.findpe2.pojos.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlacesApiService {

    @GET("{autosuggest}")
    suspend fun getAllPlaces(
        @Path("autosuggest") autoSuggest: String = "autosuggest",
        @Query("apikey") apiKey: String = API_KEY,
        @Query("name") name: String = "egypt",
        @Query("radius") radius: Long = 500000,
        @Query("lon") lon: Double = 31.233334,
        @Query("lat") lat: Double = 30.033333
    ): Response

    @GET("xid/{xid}")
    suspend fun getPlace(
        @Path("xid") xid: String,
        @Query("apikey") apiKey: String = API_KEY
    ): PlaceToVisit
}