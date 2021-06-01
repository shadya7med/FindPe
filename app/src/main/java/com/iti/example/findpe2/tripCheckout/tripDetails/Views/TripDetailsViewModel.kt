package com.iti.example.findpe2.tripCheckout.tripDetails.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.Trip

class TripDetailsViewModel(trip: Trip): ViewModel() {

    private val _selectedTrip = MutableLiveData<Trip>()
    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip

    private val _navigateToBooking = MutableLiveData<Int?>()
    val navigateToBooking: LiveData<Int?>
        get() = _navigateToBooking

    init {
        _selectedTrip.value = trip
    }

    fun displayBooking(tripId: Int){
        _navigateToBooking.value = tripId
    }
    fun displayBookingComplete(){
        _navigateToBooking.value = null
    }
}