package com.iti.example.findpe2.home.companion.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompanionViewModel: ViewModel() {

    private val _onNavigateToCompanionListData  = MutableLiveData<Boolean?>()
    val onNavigateToCompanionListData:LiveData<Boolean?>
        get() = _onNavigateToCompanionListData

    private val _onNavigateToBeCompanionHolderData  = MutableLiveData<Boolean?>()
    val onNavigateToBeCompanionHolderData:LiveData<Boolean?>
        get() = _onNavigateToBeCompanionHolderData

    fun onNavigateToCompanionList(){
        _onNavigateToCompanionListData.value = true
    }
    fun onDoneNavigationToCompanionList(){
        _onNavigateToCompanionListData.value = null
    }

    fun onNavigateToBeCompanionHolderList(){
        _onNavigateToBeCompanionHolderData.value = true
    }
    fun onDoneNavigationToBeCompanionHolder(){
        _onNavigateToBeCompanionHolderData.value = null
    }

}