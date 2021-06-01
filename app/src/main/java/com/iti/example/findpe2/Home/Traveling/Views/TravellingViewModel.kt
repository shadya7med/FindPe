package com.iti.example.findpe2.home.travelling.views

import android.util.Log
import androidx.lifecycle.*
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

private const val TAG = "TravellingViewModel"
class TravellingViewModel: ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _tripList = MutableLiveData<List<Trip>>()

    val tripList: LiveData<List<Trip>>
        get() = _tripList

    val numberOfTrips = Transformations.map(tripList){
        it.size
    }
    private val _selectedTrip = MutableLiveData<Trip>()

    val selectedTrip: LiveData<Trip>
        get() = _selectedTrip



    init {
        getTrips()
    }

    private fun getTrips() {
        viewModelScope.launch {
            try {
                _tripList.value = TripApi.retrofitTripService.getTrips()
            }catch (t: Throwable){
                Log.i(TAG, "getTrips:${t.message}")
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