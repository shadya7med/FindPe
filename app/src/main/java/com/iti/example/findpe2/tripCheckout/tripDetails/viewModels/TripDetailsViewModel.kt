package com.iti.example.findpe2.tripCheckout.tripDetails.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

enum class SaveState {
    SAVED,
    LOADING,
    NOT_SAVED
}

class TripDetailsViewModel(trip: Trip, val isSaved: Boolean) : ViewModel() {

    private val _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip

    private val _navigateToBooking = MutableLiveData<Int?>()
    val navigateToBooking: LiveData<Int?>
        get() = _navigateToBooking

    private val _navigateToTimeline = MutableLiveData<Int?>()
    val navigateToTimeline: LiveData<Int?>
        get() = _navigateToTimeline


    private val _saveState = MutableLiveData<SaveState>()
    val saveState: LiveData<SaveState>
        get() = _saveState

    init {
        _selectedTrip.value = trip
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

    fun onSaveTripClicked() {
        //show loading animation
        _saveState.value = SaveState.LOADING
        viewModelScope.launch {
            if (isSaved) {
                //the trip is saved so we should remove it
                //launch coroutine and PUT this trip as notSaved to the current user
                val list = TripApi.getAllFeaturedTrips()//for testing only
                _saveState.value = SaveState.NOT_SAVED
            } else {
                //the trip is not saved so we need to save it
                //launch coroutine and PUT this trip as saved to the current user
                val list = TripApi.getAllFeaturedTrips()//for testing only
                _saveState.value = SaveState.SAVED
            }
        }
    }

    fun displayTimeline(tripId: Int){
        _navigateToTimeline.value = tripId
    }
    fun displayTimelineComplete(){
        _navigateToTimeline.value = null
    }


}