package com.iti.example.findpe2.home.profile.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.CompanionUser

class ProfileViewModelFactory(private val isCompanion:Boolean,private val companionUser: CompanionUser?,private val isUserAlsoCompanion:Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(isCompanion,companionUser,isUserAlsoCompanion) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}