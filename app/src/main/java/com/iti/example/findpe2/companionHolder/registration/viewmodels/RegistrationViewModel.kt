package com.iti.example.findpe2.companionHolder.registration.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.CompanionUser

class RegistrationViewModel: ViewModel() {

    val companion = MutableLiveData<CompanionUser>()

    private val _navigateToLevelSelector = MutableLiveData<Boolean?>()
    val navigateToLevelSelector: LiveData<Boolean?>
        get() = _navigateToLevelSelector


    fun displayLevelSelector(){
        _navigateToLevelSelector.value = true
    }

    fun displayLevelSelectorComplete(){
        _navigateToLevelSelector.value = null
    }



}