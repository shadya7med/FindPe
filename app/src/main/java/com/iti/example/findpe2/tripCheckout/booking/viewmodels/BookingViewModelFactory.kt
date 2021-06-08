package com.iti.example.findpe2.tripCheckout.booking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.Trip
import com.iti.example.findpe2.tripCheckout.tripDetails.viewModels.TripDetailsViewModel

class BookingViewModelFactory (
    private val tripId: Int,
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
                return BookingViewModel(tripId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }