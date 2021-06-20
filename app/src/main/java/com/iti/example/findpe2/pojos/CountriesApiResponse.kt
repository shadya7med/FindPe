package com.iti.example.findpe2.pojos

data class CountriesApiResponse(
    val data: List<CountryData>
)
data class CountryData(
    val country: String,
    val cities: List<String>
)