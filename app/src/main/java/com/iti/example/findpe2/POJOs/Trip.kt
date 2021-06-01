package com.iti.example.findpe2.pojos

data class Trip(
    val id:Int,
    val code:String,
    val price:Int,
    val category: String,
    val origin:String,
    val destination:String,
    val vehicle:String,
    val location:String,
    val line:String,
    val dateTime:String,
    val tripDescription:String,
    val hotel:String,
    val companyID:Int
)

//"id":1,
//"code":"D3",
//"price":3000,
//"category":"Relaxation",
//"origin":"Alex",
//"destination":"Dahab",
//"vehicle":"Bus",
//"location":"Alex",
//"line":"G2",
//"dateTime":"2021-05-07T00:00:00",
//"tripDescription":"enjoy the stars and the calmness of dahab",
//"hotel":"Stars Hotel",
//"companyID":1,
//"company":null
