package com.iti.example.findpe2.home.saved.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

class SavedTripsViewModel : ViewModel() {


    private val _savedTripsList = MutableLiveData<List<Trip>?>()
    val savedTripsList: LiveData<List<Trip>?>
        get() = _savedTripsList

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

    private val _isLiked = MutableLiveData<Boolean?>()
    val isLiked: LiveData<Boolean?>
        get() = _isLiked

    init {
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        getAllSavedTrips()
    }



    fun getAllSavedTrips() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {
                Firebase.auth.currentUser?.let { user ->
                    val savedTrips = TripApi.getAllSavedTripsForUser(user.uid)
                    val likedTripsListIDs =
                        TripApi.getAllLikedTripsForUser(user.uid)?.map { likedTrip ->
                            likedTrip.tripID
                        }
                    //set from the combined list
                    _savedTripsList.value = savedTrips.map {
                        it.isLiked = likedTripsListIDs.indexOf(it.tripID) != -1
                        it
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

    fun onIsLikedClicked(trip: Trip) {
        viewModelScope.launch {
            Firebase.auth.currentUser?.let { user ->
                try {
                    if (trip.isLiked) {//is already liked -> unlike
                        TripApi.unLikeTripForUser(user.uid, trip.tripID)
                        val index = _savedTripsList.value?.indexOf(trip)
                        index?.let { index ->
                            if (index != -1) {
                                val list = _savedTripsList.value
                                list?.let { list ->
                                    list[index].isLiked = false
                                    _savedTripsList.value = list
                                    _isLiked.value = false
                                }
                            }
                        }
                    } else {
                        TripApi.likeTripForUser(user.uid, trip.tripID)
                        val index = _savedTripsList.value?.indexOf(trip)
                        index?.let {
                            if (it != -1) {
                                val list = _savedTripsList.value
                                list?.let { list ->
                                    list[index].isLiked = true
                                    _savedTripsList.value = list
                                    _isLiked.value = true
                                }
                            }
                        }
                    }

                } catch (e: Exception) {
                    Log.i("SavedTripsVM", e.localizedMessage)
                }
            }
        }
    }

    fun onNavigateToTripDetails(trip: Trip) {
        _onNavigateToTripDetailsData.value = trip
    }

    fun onDoneNavigationToTripDetails() {
        _onNavigateToTripDetailsData.value = null
    }


}