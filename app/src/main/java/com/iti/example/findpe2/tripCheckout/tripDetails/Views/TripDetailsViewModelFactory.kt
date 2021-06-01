package com.iti.example.findpe2.tripCheckout.tripDetails.views

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.Trip

class TripDetailsViewModelFactory (
        private val trip: Trip
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TripDetailsViewModel::class.java)) {
                return TripDetailsViewModel(trip) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

}