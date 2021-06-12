package com.iti.example.findpe2.home.travelling.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

class TravellingViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _tripList = MutableLiveData<List<Trip>?>()

    val tripList: LiveData<List<Trip>?>
        get() = _tripList

    private val _tripsListErrorMsg = MutableLiveData<String?>()
    val tripsListErrorMsg: LiveData<String?>
        get() = _tripsListErrorMsg

    val numberOfTrips = Transformations.map(tripList) {
        it?.let {
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
        getTrips()
    }

    fun getTrips() {
        _status.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _tripList.value = TripApi.getAllFeaturedTrips()
                _status.value = View.GONE
                _errorMsgStatus.value = View.GONE
            } catch (t: Throwable) {
                Log.i("TravellingViewModel", "getTrips:${t.message}")
                _errorMsgStatus.value = View.VISIBLE
                _status.value = View.GONE
                _tripsListErrorMsg.value = t.localizedMessage
            }
        }
    }

    fun filterTrips(filteringMap: Map<String, Any>?) {
        filteringMap?.let { filteringMap ->
            val fromCity = filteringMap[Keys.FROM_PLACE_KEY] as String
            val toCity = filteringMap[Keys.TO_PLACE_KEY] as String
            val placesArray = arrayOf(fromCity, toCity)

            val features = filteringMap[Keys.FEATURES_STATES_KEY] as List<Boolean>
            val featuresArray = arrayOf(
                features[0],
                features[1],
                features[2],
                features[3],
                features[4],
                true,
            )

            val minPrice = filteringMap[Keys.MIN_RANGE_KEY] as Double
            val maxPrice = filteringMap[Keys.MAX_RANGE_KEY] as Double
            val pricesArray = arrayOf(minPrice, maxPrice)

            _status.value = View.VISIBLE
            viewModelScope.launch {
                try{
                    _tripList.value =
                        TripApi.getFeaturedFilteredTrips(pricesArray, placesArray, featuresArray)
                    _status.value = View.GONE
                }catch (e:Exception){
                    _errorMsgStatus.value = View.VISIBLE
                    _status.value = View.GONE
                    _tripsListErrorMsg.value = e.localizedMessage
                    _tripList.value = null
                }

            }

        }
    }

    fun navigateToTripDetails(trip: Trip) {
        _selectedTrip.value = trip
    }

    fun navigateToTripDetailsComplete() {
        _selectedTrip.value = null
    }


}