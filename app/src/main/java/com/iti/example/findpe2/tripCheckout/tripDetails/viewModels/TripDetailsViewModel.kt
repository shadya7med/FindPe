package com.iti.example.findpe2.tripCheckout.tripDetails.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Hotel
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

enum class SaveState {
    SAVED,
    LOADING,
    NOT_SAVED
}

class TripDetailsViewModel(trip: Trip, private var isSaved: Boolean) : ViewModel() {

    private val _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip

    private val _navigateToBooking = MutableLiveData<Int?>()
    val navigateToBooking: LiveData<Int?>
        get() = _navigateToBooking

    private val _navigateToTimeline = MutableLiveData<Int?>()
    val navigateToTimeline: LiveData<Int?>
        get() = _navigateToTimeline

    private val _selectedHotel = MutableLiveData<Hotel?>()
    val selectedHotel : LiveData<Hotel?>
        get() = _selectedHotel

    private val _saveState = MutableLiveData<SaveState>()
    val saveState: LiveData<SaveState>
        get() = _saveState

    init {
        _selectedTrip.value = trip
        getHotelForID(trip.hotelID)
        _saveState.value = when (isSaved) {
            true -> SaveState.SAVED
            false -> SaveState.NOT_SAVED
        }
    }

    fun displayBooking(tripId: Int) {
        _navigateToBooking.value = tripId
    }

    fun displayBookingComplete() {
        _navigateToBooking.value = null
    }

    private fun getHotelForID(hotelID:Int){
        viewModelScope.launch {
            try{
                _selectedHotel.value = TripApi.getHotelForID(hotelID)
            }catch (e:Exception){
                Log.i("TripDetailsVM", e.localizedMessage)
            }
        }
    }

    fun onSaveTripClicked() {
        //show loading animation
        _saveState.value = SaveState.LOADING
        viewModelScope.launch {
            if (isSaved) {
                //the trip is saved so we should remove it
                //launch coroutine and PUT this trip as notSaved to the current user
                //val list = TripApi.getAllFeaturedTrips()//for testing only
                    Firebase.auth.currentUser?.let{ currentUser ->
                        _selectedTrip.value?.let{ trip ->
                            try{
                                TripApi.unSaveTripForUser(currentUser.uid,trip.tripID)
                                _saveState.value = SaveState.NOT_SAVED
                                isSaved = false
                            }catch (e:Exception){
                                Log.i("TripDetailsVM", "trips is already unSaved ${e.localizedMessage}")
                            }

                        }
                    }

            } else {
                //the trip is not saved so we need to save it
                //launch coroutine and PUT this trip as saved to the current user
                //val list = TripApi.getAllFeaturedTrips()//for testing only
                Firebase.auth.currentUser?.let{ currentUser ->
                    _selectedTrip.value?.let{ trip ->
                        try{
                            TripApi.saveTripForUser(currentUser.uid,trip.tripID)
                            _saveState.value = SaveState.SAVED
                            isSaved = true
                        }catch (e:Exception){
                            Log.i("TripDetailsVM", "trips is already saved ${e.localizedMessage}")
                        }
                    }
                }
                /*val currentUser = Firebase.auth.currentUser
                currentUser?.uid?.let { uid ->
                    _selectedTrip.value?.let { trip ->
                        try {
                            val response = TripApi.likeBookOrSaveATrip(
                                UserTrip(
                                    userID = uid,
                                    tripID = trip.tripID,
                                    likeORBookORSave = TripOpState.SAVE.value
                                )
                            )
                            _saveState.value = SaveState.SAVED
                        } catch (e: Exception) {
                            _saveState.value = SaveState.NOT_SAVED
                            Log.i("tripDetails ", e.localizedMessage)

                        }
                    }

                }*/

            }

        }

    }

    fun displayTimeline(tripId: Int) {
        _navigateToTimeline.value = tripId
    }

    fun displayTimelineComplete() {
        _navigateToTimeline.value = null
    }


}