package com.iti.example.findpe2.pojos

enum class UserInfoTitleType(val value:String){
    TRIPS_HISTORY("Trips History"),
    WORK_HISTORY("Work History"),
    PERKS("Perks"),
    Languages("Languages")
}
data class UserInfo(
    val title:UserInfoTitleType,
    val subTitles:ArrayList<String>
)
