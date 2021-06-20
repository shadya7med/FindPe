package com.iti.example.findpe2.home.companion.viewModels

import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Companion
import kotlinx.coroutines.launch

class CompanionViewModel : ViewModel() {
    private val _companionList = MutableLiveData<List<Companion>?>()

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
            View.VISIBLE
        }
    }

    val jobListVisibility = Transformations.map(joinUsVisibility) {
        if (it != null) {
            if (it == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
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
}