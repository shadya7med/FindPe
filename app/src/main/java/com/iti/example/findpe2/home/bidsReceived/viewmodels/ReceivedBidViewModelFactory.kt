package com.iti.example.findpe2.home.bidsReceived.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.home.timeline.viewmodels.TimelineViewModel

class ReceivedBidViewModelFactory (
    private val jobId: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceivedBidViewModel::class.java)) {
            return ReceivedBidViewModel(jobId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}