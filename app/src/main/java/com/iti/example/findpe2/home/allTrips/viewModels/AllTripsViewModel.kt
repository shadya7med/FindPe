package com.iti.example.findpe2.home.allTrips.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

class AllTripsViewModel:ViewModel() {

    private val _allTripsList= MutableLiveData<List<Trip>?>()
    val allTripsList:LiveData<List<Trip>?>
        get() = _allTripsList


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

    private val _onNavigateToTripDetailsData = MutableLiveData<Trip?>()
    val selectedTrip:LiveData<Trip?>
        get() = _onNavigateToTripDetailsData

    init {
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        //getAllTrips()

    }

    fun setAllTrips(allTrips: List<Trip>){
        _allTripsList.value = allTrips
    }

    fun getAllTrips(){
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try{
                _allTripsList.value = TripApi.getAllTrips()
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.GONE
                _allTripsList.value?.let{
                    if (it.isNullOrEmpty()){
                        _emptyListStatus.value = View.VISIBLE
                    }else{
                        _emptyListStatus.value = View.GONE
                    }
                }

            }catch (e:Exception){
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.VISIBLE
                _emptyListStatus.value = View.GONE
                _errorMsg.value = e.localizedMessage
            }


        }
    }

    fun onNavigateToTripDetails(trip:Trip){
        _onNavigateToTripDetailsData.value = trip
    }
    fun onDoneNavigationToTripDetails(){
        _onNavigateToTripDetailsData.value = null
    }

}