package com.iti.example.findpe2.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient

class HomeViewModelFactory(
    val locationProvider: FusedLocationProviderClient,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(locationProvider,application) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}