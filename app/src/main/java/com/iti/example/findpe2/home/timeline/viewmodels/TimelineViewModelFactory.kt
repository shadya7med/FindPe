package com.iti.example.findpe2.home.timeline.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.Trip
import com.iti.example.findpe2.tripCheckout.tripDetails.views.TripDetailsViewModel

class TimelineViewModelFactory (
    private val tripId: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimelineViewModel::class.java)) {
            return TimelineViewModel(tripId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}