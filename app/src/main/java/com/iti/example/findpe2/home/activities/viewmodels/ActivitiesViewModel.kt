package com.iti.example.findpe2.home.activities.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivitiesViewModel: ViewModel() {

    private val _selectedCategory = MutableLiveData<String?>()
    val selectedCategory: LiveData<String?>
        get() = _selectedCategory

    private val _navigateToCategoryTrips = MutableLiveData<String?>()
    val navigateToCategoryTrips: LiveData<String?>
        get() = _navigateToCategoryTrips


    fun displayCategoryTrip(category: String){
        _navigateToCategoryTrips.value = category
    }

    fun displayCompleted(){
        _navigateToCategoryTrips.value = null
    }

}