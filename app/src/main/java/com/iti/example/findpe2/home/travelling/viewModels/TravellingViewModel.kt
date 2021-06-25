package com.iti.example.findpe2.home.travelling.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private var allFeaturedTrips: List<Trip>? = null
    private var savedTripsListIDs: List<Int>? = null

    private val _tripsListErrorMsg = MutableLiveData<String?>()
    val tripsListErrorMsg: LiveData<String?>
        get() = _tripsListErrorMsg

    private val _isFilteredShown = MutableLiveData<Int?>()
    val isFilteredShown: LiveData<Int?>
        get() = _isFilteredShown

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

    private var isSaved: Boolean? = false

    init {
        _status.value = View.GONE
        _errorMsgStatus.value = View.GONE
        _isFilteredShown.value = View.GONE
        getTrips()
    }



    fun getTripSaveState() = isSaved

    fun getTrips() {
        _status.value = View.VISIBLE
        viewModelScope.launch {
            try {
                allFeaturedTrips = TripApi.getAllFeaturedTrips()
                _tripList.value = allFeaturedTrips
                Firebase.auth.currentUser?.let { user ->
                    savedTripsListIDs =
                        TripApi.getAllSavedTripsForUser(user.uid).map { trip ->
                            trip.tripID
                        }
                    _status.value = View.GONE
                    _errorMsgStatus.value = View.GONE
                    _isFilteredShown.value = View.GONE
                }
            } catch (t: Throwable) {
                Log.i("TravellingViewModel", "getTrips:${t.message}")
                _errorMsgStatus.value = View.VISIBLE
                _status.value = View.GONE
                _tripsListErrorMsg.value = t.localizedMessage
                _isFilteredShown.value = View.GONE
            }
        }
    }

    fun filterTrips(filteringMap: Map<String, Any>?) {
        filteringMap?.let { filteringMap ->
            val fromPlace = filteringMap[Keys.FROM_PLACE_KEY] as String
            val toPlace = filteringMap[Keys.TO_PLACE_KEY] as String


            val features = filteringMap[Keys.FEATURES_STATES_KEY] as List<Boolean>


            val minPrice = filteringMap[Keys.MIN_RANGE_KEY] as Double
            val maxPrice = filteringMap[Keys.MAX_RANGE_KEY] as Double


            _status.value = View.VISIBLE
            viewModelScope.launch {
                try {
                    //still not working properly
                    _tripList.value = TripApi.getFilteredTrips(
                        minPrice, maxPrice, fromPlace, toPlace,
                        features[0], features[1], features[2], features[3], features[4],
                        true
                    )
                    _isFilteredShown.value = View.VISIBLE
                    _status.value = View.GONE
                } catch (e: Exception) {
                    _errorMsgStatus.value = View.VISIBLE
                    _status.value = View.GONE
                    _tripsListErrorMsg.value = e.localizedMessage
                    _tripList.value = allFeaturedTrips
                    _isFilteredShown.value = View.GONE
                }

            }

        }
    }

    fun navigateToTripDetails(trip: Trip) {
        isSaved = savedTripsListIDs?.contains(trip.tripID)
        _selectedTrip.value = trip
    }

    fun navigateToTripDetailsComplete() {
        _selectedTrip.value = null
    }


}