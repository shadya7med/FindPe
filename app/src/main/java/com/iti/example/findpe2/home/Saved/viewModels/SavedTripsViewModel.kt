package com.iti.example.findpe2.home.saved.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.pojos.Trip

class SavedTripsViewModel: ViewModel() {


    private val _savedTripsList = MutableLiveData<List<Trip>?>()
    val savedTripsList:LiveData<List<Trip>?>
        get() = _savedTripsList

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg:LiveData<String?>
        get() = _errorMsg

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus:LiveData<Int?>
        get() = _loadingStatus

    private val _errorStatus = MutableLiveData<Int?>()
    val errorStatus:LiveData<Int?>
        get() = _errorStatus

    private val _emptyListStatus = MutableLiveData<Int?>()
    val emptyListStatus:LiveData<Int?>
        get() = _emptyListStatus

    init{
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        //getAllSavedTrips
    }

    fun getFilteredTrips(result:MutableMap<String,Any>){
        val priceList = listOf<Double>(result[Keys.MIN_RANGE_KEY] as Double,result[Keys.MAX_RANGE_KEY] as Double)
        val placesList = listOf<String>(result[Keys.FROM_PLACE_KEY] as String,result[Keys.TO_PLACE_KEY] as String)
        @Suppress("UNCHECKED_CAST")
        val featuresList = result[Keys.FEATURES_STATES_KEY] as MutableList<Boolean>
        //call API.getFiltered
    }




}