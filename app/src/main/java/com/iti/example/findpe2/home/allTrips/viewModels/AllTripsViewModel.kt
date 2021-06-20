package com.iti.example.findpe2.home.allTrips.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

class AllTripsViewModel : ViewModel() {

    private val _allTripsList = MutableLiveData<List<Trip>?>()
    val allTripsList: LiveData<List<Trip>?>
        get() = _allTripsList

    private var allTrips: List<Trip>? = null

    private var savedTripsListIDs: List<Int>? = null

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?>
        get() = _errorMsg

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus: LiveData<Int?>
        get() = _loadingStatus

    private val _errorStatus = MutableLiveData<Int?>()
    val errorStatus: LiveData<Int?>
        get() = _errorStatus

    private val _emptyListStatus = MutableLiveData<Int?>()
    val emptyListStatus: LiveData<Int?>
        get() = _emptyListStatus

    private val _onNavigateToFilter = MutableLiveData<Boolean?>()
    val onNavigateToFilter: LiveData<Boolean?>
        get() = _onNavigateToFilter

    private val _onNavigateToTripDetailsData = MutableLiveData<Trip?>()
    val selectedTrip: LiveData<Trip?>
        get() = _onNavigateToTripDetailsData

    private val _isFilteredShown = MutableLiveData<Int?>()
    val isFilteredShown: LiveData<Int?>
        get() = _isFilteredShown

    private var isSaved: Boolean? = false


    init {
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        _isFilteredShown.value = View.GONE
        //getAllTrips()

    }

    fun setAllTrips(allTrips: List<Trip>, allSavedTripsIDs: List<Int>) {
        _allTripsList.value = allTrips
        savedTripsListIDs = allSavedTripsIDs
    }

    fun getTripSaveState() = isSaved

    fun getAllTrips() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {
                allTrips = TripApi.getAllTrips()
                _allTripsList.value = allTrips
                Firebase.auth.currentUser?.let { user ->
                    savedTripsListIDs =
                        TripApi.getAllSavedTripsForUser(user.uid).map { trip ->
                            trip.tripID
                        }
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.GONE
                    _isFilteredShown.value = View.GONE
                    _allTripsList.value?.let {
                        if (it.isNullOrEmpty()) {
                            _emptyListStatus.value = View.VISIBLE
                        } else {
                            _emptyListStatus.value = View.GONE
                        }
                    }
                }

            } catch (e: Exception) {
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.VISIBLE
                _emptyListStatus.value = View.GONE
                _errorMsg.value = e.localizedMessage
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

            _loadingStatus.value = View.VISIBLE
            viewModelScope.launch {
                try {
                    //still not working properly
                    _allTripsList.value = TripApi.getFilteredTrips(
                        minPrice, maxPrice, fromPlace, toPlace,
                        features[0], features[1], features[2], features[3], features[4],
                        false
                    )
                    _isFilteredShown.value = View.VISIBLE
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.GONE
                } catch (e: Exception) {
                    _errorStatus.value = View.VISIBLE
                    _loadingStatus.value = View.GONE
                    _errorMsg.value = e.localizedMessage
                    _allTripsList.value = allTrips
                    _isFilteredShown.value = View.GONE
                }

            }

        }
    }

    fun onNavigateToFilter() {
        _onNavigateToFilter.value = true
    }

    fun onDoneNavigationToFilter() {
        _onNavigateToFilter.value = null
    }

    fun onNavigateToTripDetails(trip: Trip) {
        isSaved = savedTripsListIDs?.contains(trip.tripID)
        _onNavigateToTripDetailsData.value = trip
    }

    fun onDoneNavigationToTripDetails() {
        _onNavigateToTripDetailsData.value = null
    }

}