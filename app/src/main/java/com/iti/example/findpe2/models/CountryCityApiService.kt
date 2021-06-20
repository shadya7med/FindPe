package com.iti.example.findpe2.models

import com.iti.example.findpe2.pojos.CountriesApiResponse
import retrofit2.http.GET

interface CountryCityApiService {

    @GET("countries")
    suspend fun getAllCountries(): CountriesApiResponse
}