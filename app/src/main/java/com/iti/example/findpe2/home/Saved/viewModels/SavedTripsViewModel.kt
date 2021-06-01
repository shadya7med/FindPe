package com.iti.example.findpe2.home.saved.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.Trip

class SavedTripsViewModel: ViewModel() {


    private val _savedTripsList = MutableLiveData<List<Trip>?>()
    val savedTripsList:LiveData<List<Trip>?>
        get() = _savedTripsList

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg:LiveData<String?>
        get() = _errorMsg




}