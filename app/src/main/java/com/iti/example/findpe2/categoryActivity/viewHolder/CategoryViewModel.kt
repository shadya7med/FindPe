package com.iti.example.findpe2.categoryActivity.viewHolder

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryName: String): ViewModel() {

    private val _tripList = MutableLiveData<List<Trip>?>()

    val tripList: LiveData<List<Trip>?>
        get() = _tripList

    private val _tripsListErrorMsg = MutableLiveData<String?>()
    val tripsListErrorMsg: LiveData<String?>
        get() = _tripsListErrorMsg

    val emptyListStatus = Transformations.map(tripList){
        if(it != null){
            if(it.isEmpty())
                View.VISIBLE
            else
                View.GONE
        }else{
            View.GONE
        }
    }

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus: LiveData<Int?>
        get() = _loadingStatus

    private val _errorMsgStatus = MutableLiveData<Int?>()
    val errorMsgStatus: LiveData<Int?>
        get() = _errorMsgStatus

    private val _selectedTrip = MutableLiveData<Trip?>()
    val selectedTrip: LiveData<Trip?>
        get() = _selectedTrip


    init {
        _tripList.value = null
        _loadingStatus.value = View.GONE
        _errorMsgStatus.value = View.GONE
        getTrips()
    }

    private fun getTrips() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _tripList.value = TripApi.getTripsByCategory(categoryName)
                _loadingStatus.value = View.GONE
                _errorMsgStatus.value = View.GONE
            }catch (t: Throwable){
                Log.i("TravellingViewModel", "getTrips:${t.message}")
                _errorMsgStatus.value = View.VISIBLE
                _loadingStatus.value = View.GONE
                _tripsListErrorMsg.value  = t.localizedMessage
            }
        }
    }

    fun navigateToTripDetails(trip: Trip){
        _selectedTrip.value = trip
    }
    fun navigateToTripDetailsComplete(){
        _selectedTrip.value = null
    }

}