package com.iti.example.findpe2.jobHolder.browseJobs.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Job
import kotlinx.coroutines.launch

const val TAG = "BrowseViewModel"
class BrowseJobsViewModel : ViewModel() {
    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility

    private val _jobList = MutableLiveData<List<Job>?>()
    val jobList: LiveData<List<Job>?>
        get() = _jobList

    init {
        _loadingVisibility.value = View.GONE
        getAllJobs()
    }

    private fun getAllJobs() {
        _loadingVisibility.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _jobList.value = TripApi.getAllJobs()
                _loadingVisibility.value = View.GONE
            } catch (t: Throwable) {
                Log.i(TAG, "getAllJobs: ${t.localizedMessage}")
            }

        }
    }


}