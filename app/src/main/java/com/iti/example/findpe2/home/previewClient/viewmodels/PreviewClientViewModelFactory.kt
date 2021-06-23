package com.iti.example.findpe2.home.previewClient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PreviewClientViewModelFactory(private val clientID: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreviewClientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PreviewClientViewModel(clientID) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}