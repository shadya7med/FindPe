package com.iti.example.findpe2.tripCheckout.tripDetails.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.Trip

class TripDetailsViewModelFactory(
    private val trip: Trip,
    private val isSaved: Boolean
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripDetailsViewModel::class.java)) {
            return TripDetailsViewModel(trip,isSaved) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}