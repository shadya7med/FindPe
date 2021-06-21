package com.iti.example.findpe2.home.profile.bio.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.CompanionUser

class EditBioViewModelFactory(private val currentCompanion: CompanionUser?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditBioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditBioViewModel(currentCompanion) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}