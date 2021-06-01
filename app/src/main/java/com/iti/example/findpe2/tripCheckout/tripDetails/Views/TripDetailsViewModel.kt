package com.iti.example.findpe2.tripCheckout.tripDetails.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.Trip

class TripDetailsViewModel(trip: Trip): ViewModel() {

    private val _selectedTrip = MutableLiveData<Trip>()

    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip

    init {
        _selectedTrip.value = trip
    }
}