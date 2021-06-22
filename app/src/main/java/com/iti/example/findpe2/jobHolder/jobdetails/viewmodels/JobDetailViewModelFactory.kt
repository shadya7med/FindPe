package com.iti.example.findpe2.jobHolder.jobdetails.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.Job
import com.iti.example.findpe2.pojos.ReceivedJobRequest

class JobDetailViewModelFactory(
private val request: ReceivedJobRequest?,
private val job: Job?
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobDetailViewModel::class.java)) {
            return JobDetailViewModel(request, job) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}