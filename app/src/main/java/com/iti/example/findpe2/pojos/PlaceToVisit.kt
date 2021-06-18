package com.iti.example.findpe2.pojos

data class Response(
    val features: List<Feature>
)
data class Feature(
    val properties: Properties
)
data class Properties(
    val xid: String
)
data class PlaceToVisit(
    val xid: String,
    val name: String,
    val address: Address,
    val preview: Preview,
    val point: Point
)
data class Point(
    val lon: Double,
    val lat: Double
)
data class Address(
    val city: String
)
data class Preview(
    val source: String
)