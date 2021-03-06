package com.iti.example.findpe2.models

import com.iti.example.findpe2.pojos.*
import retrofit2.http.*

//BASE URL --> http://rsaber-001-site1.ftempurl.com/api/
interface TripsApiService {

    @GET("Trips")
    suspend fun getTrips(): List<Trip>

    @GET("Trips/Featuredlist/true")
    suspend fun getAllFeaturedTrips(): List<Trip>

    @GET("Trips/FillterByCategory/{category}")
    suspend fun getTripsByCategory(
        @Path("category") category: Int,
    ): List<Trip>

    @GET("TripDuration/GetTripDurations/{id}")
    suspend fun getTripDurations(@Path("id") id: Int): List<TripDuration>

    @GET("TimeLine/GetTimeLineToTripByID/{id}")
    suspend fun getTimelineSlot(@Path("id") id: Int): List<TimelineSlot>

    @GET("Trips/CityTo")
    suspend fun getAllToCitiesName(): List<String>

    @GET("Trips/CityFrom")
    suspend fun getAllFromCitiesName(): List<String>


    @GET("CalculateTrip")
    suspend fun getFilteredTrips(
        @Query("price") minPrice: Double,
        @Query("price") maxPrice: Double,
        @Query("place") fromPlace: String,
        @Query("place") toPlace: String,
        @Query("featured") wifi: Boolean,
        @Query("featured") restaurant: Boolean,
        @Query("featured") pool: Boolean,
        @Query("featured") inn: Boolean,
        @Query("featured") parking: Boolean,
        @Query("featured") isFeatured: Boolean,
    ): List<Trip>

    //user requests
    @POST("User")
    suspend fun addANewUser(@Body user: User)//:User

    @GET("User/{id}")
    suspend fun getUserByID(@Path("id") userID: String): User

    @GET("User")
    suspend fun getAllUsers(): List<User>

    //user Images
    @POST("UserImages/Upload/{id}")
    suspend fun uploadImageUrlForUser(
        @Path("id") id: String,
        @Query("file") profileImageUrl: String
    )

    @GET("UserImages/{id}")
    suspend fun getAllProfileUserImages(@Path("id") id: String): List<String>

    //user-trips requests
    @GET("UserTrip/GetTripSaved/{userID}")
    suspend fun getAllSavedTripsForUserByID(@Path("userID") userID: String): List<Trip>

    @GET("UserTrip/GetTripLiked/{userID}")
    suspend fun getAllLikedTripsForUserByID(@Path("userID") userID: String): List<Trip>

    @GET("UserTrip/GetTripBooked/{userID}")
    suspend fun getAllBookedTripsForUserByID(@Path("userID") userID: String): List<Trip>

    @PUT("UserTrip/Save")
    suspend fun saveTripForUser(
        @Query("userID") userID: String,
        @Query("tripID") tripID: Int
    )//:UserTrip

    @PUT("UserTrip/Book")
    suspend fun bookTripForUser(@Query("userID") userID: String, @Query("tripID") tripID: Int)

    @PUT("UserTrip/Like")
    suspend fun likeTripForUser(@Query("userID") userID: String, @Query("tripID") tripID: Int)

    @PUT("UserTrip/UNSave")
    suspend fun unSaveTripForUser(@Query("userID") userID: String, @Query("tripID") tripID: Int)

    @PUT("UserTrip/UNBook")
    suspend fun unBookTripForUser(@Query("userID") userID: String, @Query("tripID") tripID: Int)

    @PUT("UserTrip/UNLike")
    suspend fun unLikeTripForUser(@Query("userID") userID: String, @Query("tripID") tripID: Int)


    @DELETE("UserTrip")
    suspend fun deleteTripForUser(@Query("userID") userID: String, @Query("tripID") tripID: Int)


    //Companion
    @GET("Companion")
    suspend fun getAllCompanions(): List<CompanionUser>

    @GET("Companion/{id}")
    suspend fun getACompanionById(@Path("id")id:String):CompanionUser

    @POST("Companion")
    suspend fun addANewsCompanion(@Body companionUser: CompanionUser)

    @PUT("Companion")
    suspend fun updateACompanion(@Body companionUser: CompanionUser)


    //job
    @GET("Jop")
    suspend fun getAllJobs(): List<Job>

    @POST("Jop")
    suspend fun addANewJob(@Body job: Job)

    @PUT("Jop")
    suspend fun updateJob(@Body job: Job)

    @DELETE("Jop/{id}")
    suspend fun deleteJob(@Path("id") jobId: Int,@Query("jopID") jobID: Int)

    //hotel
    @GET("Hotel/{id}")
    suspend fun getHotelForID(@Path("id")hotelID:Int):Hotel


}