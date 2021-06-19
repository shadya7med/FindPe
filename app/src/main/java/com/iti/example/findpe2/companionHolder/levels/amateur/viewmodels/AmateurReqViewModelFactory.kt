package com.iti.example.findpe2.companionHolder.levels.amateur.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.companionHolder.levels.professional.viewmodels.ProReqViewModel
import com.iti.example.findpe2.pojos.RegistrationInfo

class AmateurReqViewModelFactory (
    private val registrationInfo: RegistrationInfo
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AmateurReqViewModel::class.java)) {
            return AmateurReqViewModel(registrationInfo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}