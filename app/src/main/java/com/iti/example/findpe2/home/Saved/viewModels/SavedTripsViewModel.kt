package com.iti.example.findpe2.home.saved.viewModels

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

class SavedTripsViewModel : ViewModel() {


    private val _savedTripsList = MutableLiveData<List<Trip>?>()
    val savedTripsList: LiveData<List<Trip>?>
        get() = _savedTripsList

    //private var savedTripsListIDs: List<Int>? = null

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

    private val _onNavigateToTripDetailsData = MutableLiveData<Trip?>()
    val onNavigateToTripDetailsData: LiveData<Trip?>
        get() = _onNavigateToTripDetailsData

    //private var isSaved: Boolean? = false

    init {
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        //getAllSavedTrips
        //called in onStart otherwise to refresh also when returning form trip details
        //getAllSavedTrips()
    }

    //fun getTripSaveState() = isSaved

    fun getFilteredTrips(result: MutableMap<String, Any>) {
        val priceList = listOf<Double>(
            result[Keys.MIN_RANGE_KEY] as Double,
            result[Keys.MAX_RANGE_KEY] as Double
        )
        val placesList = listOf<String>(
            result[Keys.FROM_PLACE_KEY] as String,
            result[Keys.TO_PLACE_KEY] as String
        )

        @Suppress("UNCHECKED_CAST")
        val featuresList = result[Keys.FEATURES_STATES_KEY] as MutableList<Boolean>
        //call API.getFiltered
    }

    fun getAllSavedTrips() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {
                Firebase.auth.currentUser?.let { user ->
                    _savedTripsList.value = TripApi.getAllSavedTripsForUser(user.uid)
                    /*savedTripsListIDs = _savedTripsList.value?.map { trip ->
                        trip.tripID
                    }*/
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.GONE
                    _savedTripsList.value?.let {
                        if (it.isEmpty()) {
                            _emptyListStatus.value = View.VISIBLE
                        } else {
                            _emptyListStatus.value = View.GONE
                        }
                    }
                }
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.GONE
                _savedTripsList.value?.let {
                    if (it.isEmpty()) {
                        _emptyListStatus.value = View.VISIBLE
                    } else {
                        _emptyListStatus.value = View.GONE
                    }
                }
            } catch (e: Exception) {
                _errorMsg.value = e.localizedMessage
                _errorStatus.value = View.VISIBLE
                _loadingStatus.value = View.GONE
                _emptyListStatus.value = View.GONE

            }

        }
    }

    fun onNavigateToTripDetails(trip: Trip) {
        //isSaved = savedTripsListIDs?.contains(trip.tripID)
        _onNavigateToTripDetailsData.value = trip
    }

    fun onDoneNavigationToTripDetails() {
        _onNavigateToTripDetailsData.value = null
    }


}