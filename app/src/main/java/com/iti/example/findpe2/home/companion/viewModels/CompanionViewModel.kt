package com.iti.example.findpe2.home.companion.viewModels

import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.CompanionUser
import kotlinx.coroutines.launch

class CompanionViewModel : ViewModel() {
    private val _companionList = MutableLiveData<List<CompanionUser>?>()

    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility

    val joinUsVisibility = Transformations.map(_companionList) {
        if (it != null) {
            val companionIdList = it.map { companion ->
                companion.companionID
            }
            if (companionIdList.contains(FirebaseAuth.getInstance().currentUser!!.uid)) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        } else {
            View.INVISIBLE
        }
    }

    val jobListVisibility = Transformations.map(_companionList) {
        if (it != null) {
            val companionIdList = it.map { companion ->
                companion.companionID
            }
            if (companionIdList.contains(FirebaseAuth.getInstance().currentUser!!.uid)) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        } else {
            View.INVISIBLE
        }
    }

    private val _onNavigateToCompanionListData = MutableLiveData<Boolean?>()
    val onNavigateToCompanionListData: LiveData<Boolean?>
        get() = _onNavigateToCompanionListData

    private val _onNavigateToBeCompanionHolderData = MutableLiveData<Boolean?>()
    val onNavigateToBeCompanionHolderData: LiveData<Boolean?>
        get() = _onNavigateToBeCompanionHolderData

    private val _navigateToJobRequest = MutableLiveData<Boolean?>()
    val  navigateToJobRequest: LiveData<Boolean?>
        get() =  _navigateToJobRequest

    private val _navigateToBrowseJob = MutableLiveData<Boolean?>()
    val  navigateToBrowseJob: LiveData<Boolean?>
        get() =  _navigateToBrowseJob

    fun onNavigateToCompanionList() {
        _onNavigateToCompanionListData.value = true
    }

    fun onDoneNavigationToCompanionList() {
        _onNavigateToCompanionListData.value = null
    }

    fun onNavigateToBeCompanionHolderList() {
        _onNavigateToBeCompanionHolderData.value = true
    }

    fun onDoneNavigationToBeCompanionHolder() {
        _onNavigateToBeCompanionHolderData.value = null
    }

    init {
        _loadingVisibility.value = View.GONE
        _companionList.value = null
    }

    fun getCompanionList() {
        _loadingVisibility.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _companionList.value = TripApi.getAllCompanions()
                _loadingVisibility.value = View.GONE
            } catch (t: Throwable) {

            }
        }
    }
    fun displayJobRequestActivity(){
        _navigateToJobRequest.value = true
    }
    fun displayJobRequestActivityCompleted(){
        _navigateToJobRequest.value = null
    }

    fun displayBrowseJobActivity(){
        _navigateToBrowseJob.value = true
    }
    fun displayBrowseJobActivityCompleted(){
        _navigateToBrowseJob.value = null
    }
}