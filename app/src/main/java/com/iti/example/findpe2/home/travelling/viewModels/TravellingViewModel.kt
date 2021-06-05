package com.iti.example.findpe2.home.travelling.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

class TravellingViewModel: ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _tripList = MutableLiveData<List<Trip>?>()

    val tripList: LiveData<List<Trip>?>
        get() = _tripList

    private val _tripsListErrorMsg = MutableLiveData<String?>()
    val tripsListErrorMsg:LiveData<String?>
        get() = _tripsListErrorMsg

    val numberOfTrips = Transformations.map(tripList){
        it?.let{
            it.size
        }
    }

    private val _status = MutableLiveData<Int?>()
    val status: LiveData<Int?>
        get() = _status

    private val _errorMsgStatus = MutableLiveData<Int?>()
    val errorMsgStatus: LiveData<Int?>
        get() = _errorMsgStatus

    private val _selectedTrip = MutableLiveData<Trip?>()
    val selectedTrip: LiveData<Trip?>
        get() = _selectedTrip


    init {
        _status.value = View.GONE
        _errorMsgStatus.value = View.GONE
        //getTrips()
    }

    fun getTrips() {
        _status.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _tripList.value = TripApi.getAllTrips()
                _status.value = View.GONE
                _errorMsgStatus.value = View.GONE
            }catch (t: Throwable){
                Log.i("TravellingViewModel", "getTrips:${t.message}")
                _errorMsgStatus.value = View.VISIBLE
                _status.value = View.GONE
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