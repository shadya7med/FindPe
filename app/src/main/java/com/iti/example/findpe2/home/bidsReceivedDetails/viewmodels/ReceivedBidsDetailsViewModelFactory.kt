package com.iti.example.findpe2.home.bidsReceivedDetails.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.home.bidsReceived.viewmodels.ReceivedBidViewModel
import com.iti.example.findpe2.pojos.ReceivedBid

class ReceivedBidsDetailsViewModelFactory (
    private val receivedBid: ReceivedBid
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceivedBidsDetailsViewModel::class.java)) {
            return ReceivedBidsDetailsViewModel(receivedBid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}