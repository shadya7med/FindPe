package com.iti.example.findpe2.models

import com.iti.example.findpe2.constants.URLs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryCityApiModel {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())//MoshiConverterFactory.create(moshi))
        .baseUrl(URLs.COUNTRIES_CITY_BASE_URL)//BASE_URL)
        .build()


    private val countryApi: CountryCityApiService by lazy {
        CountryCityApiModel.retrofit.create(CountryCityApiService::class.java)
    }

    suspend fun getAllCountries() = countryApi.getAllCountries()
}