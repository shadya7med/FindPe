package com.iti.example.findpe2.companionHolder.levels.professional.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.home.timeline.viewmodels.TimelineViewModel
import com.iti.example.findpe2.pojos.RegistrationInfo

class ProReqViewModelFactory(
    private val registrationInfo: RegistrationInfo
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProReqViewModel::class.java)) {
            return ProReqViewModel(registrationInfo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}