package com.iti.example.findpe2.models

import com.iti.example.findpe2.constants.URLs.Companion.NOT_SECURE_BASE_URL
import com.iti.example.findpe2.pojos.CompanionUser
import com.iti.example.findpe2.pojos.Job
import com.iti.example.findpe2.pojos.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofitTripApi Singleton
object TripApi {

    //Create retrofit object using bulider and give it converter factory moshi
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())//MoshiConverterFactory.create(moshi))
        .baseUrl(NOT_SECURE_BASE_URL)//BASE_URL)
        .build()


    private val tripsApi: TripsApiService by lazy {
        retrofit.create(TripsApiService::class.java)
    }

    suspend fun getAllTrips() = tripsApi.getTrips()
    suspend fun getAllFeaturedTrips() = tripsApi.getAllFeaturedTrips()
    suspend fun getTripsByCategory(id: Int) =
        tripsApi.getTripsByCategory(id)

    suspend fun getTimelineSlot(tripId: Int) = tripsApi.getTimelineSlot(tripId)
    suspend fun getTripDurations(tripId: Int) = tripsApi.getTripDurations(tripId)
    suspend fun getAllFromCities() = tripsApi.getAllFromCitiesName()
    suspend fun getAllToCities() = tripsApi.getAllToCitiesName()
    suspend fun getFilteredTrips(
        minPrice: Double,
        maxPrice: Double,
        fromPlace: String,
        toPlace: String,
        wifi: Boolean,
        restaurant: Boolean,
        pool: Boolean,
        inn: Boolean,
        parking: Boolean,
        isFeatured: Boolean,
    ) = tripsApi.getFilteredTrips(
        minPrice, maxPrice, fromPlace, toPlace,
        wifi, restaurant, pool, inn, parking,
        isFeatured
    )

    suspend fun addANewUser(user: User) = tripsApi.addANewUser(user)
    suspend fun getUserByID(userID: String) = tripsApi.getUserByID(userID)
    suspend fun getAllUsers() = tripsApi.getAllUsers()

    suspend fun uploadImageForUser(id: String, profileImageUrl: String) =
        tripsApi.uploadImageUrlForUser(id, profileImageUrl)

    suspend fun getAllImagesForUser(id: String) = tripsApi.getAllProfileUserImages(id)

    suspend fun getAllSavedTripsForUser(userID: String) =
        tripsApi.getAllSavedTripsForUserByID(userID)

    suspend fun getAllLikedTripsForUser(userID: String) =
        tripsApi.getAllLikedTripsForUserByID(userID)

    suspend fun getAllBookedTripsForUser(userID: String) =
        tripsApi.getAllBookedTripsForUserByID(userID)

    suspend fun saveTripForUser(userID: String, tripId: Int) =
        tripsApi.saveTripForUser(userID, tripId)

    suspend fun unSaveTripForUser(userID: String, tripId: Int) =
        tripsApi.unSaveTripForUser(userID, tripId)

    suspend fun bookTripForUser(userID: String, tripId: Int) =
        tripsApi.bookTripForUser(userID, tripId)

    suspend fun unBookTripForUser(userID: String, tripId: Int) =
        tripsApi.unBookTripForUser(userID, tripId)

    suspend fun likeTripForUser(userID: String, tripId: Int) =
        tripsApi.likeTripForUser(userID, tripId)

    suspend fun unLikeTripForUser(userID: String, tripId: Int) =
        tripsApi.unLikeTripForUser(userID, tripId)

    suspend fun deleteTripForUser(userID: String, tripId: Int) =
        tripsApi.deleteTripForUser(userID, tripId)

    suspend fun addANewCompanion(companionUser: CompanionUser) =
        tripsApi.addANewsCompanion(companionUser)
    suspend fun getACompanionById(id:String) = tripsApi.getACompanionById(id)
    suspend fun updateACompanion(companionUser: CompanionUser) =
        tripsApi.updateACompanion(companionUser)
    suspend fun getAllCompanions() = tripsApi.getAllCompanions()


    //job

    suspend fun addANewJob(job: Job) = tripsApi.addANewJob(job)
    suspend fun updateJob(job: Job) = tripsApi.updateJob(job)
    suspend fun getAllJobs() = tripsApi.getAllJobs()

    //hotel
    suspend fun getHotelForID(hotelID:Int) = tripsApi.getHotelForID(hotelID)

}
