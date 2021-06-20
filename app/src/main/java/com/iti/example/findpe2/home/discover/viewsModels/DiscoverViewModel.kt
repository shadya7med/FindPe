package com.iti.example.findpe2.home.discover.viewsModels

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.models.PlacesApiModel
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Feature
import com.iti.example.findpe2.pojos.PlaceToVisit
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch
import kotlin.random.Random

const val TAG = "DiscoverViewModel"
class DiscoverViewModel : ViewModel() {

    private val _allTripsList = MutableLiveData<List<Trip>?>()
    val allTripsList: LiveData<List<Trip>?>
        get() = _allTripsList

    private var savedTripsListIDs: List<Int>? = null

    private val _fourRandomTrips = Transformations.map(_allTripsList) { allTrips ->
        allTrips?.let {
            if (it.size > 4) {
                val randomTrips = mutableListOf<Trip>()
                val randomIndexes = arrayListOf<Int>()
                for (index in 0..3) {
                    var randomInt = Random.nextInt(0, it.size)
                    while (randomIndexes.contains(randomInt)) {
                        randomInt = Random.nextInt(0, it.size)
                    }
                        randomIndexes.add(randomInt)

                }
                for (index in randomIndexes) {
                    randomTrips.add(it[index])
                }
                randomTrips
            } else {
                it
            }
        }

    }
    val fourRandomTrips: LiveData<List<Trip>?>
        get() = _fourRandomTrips

    private val _features = MutableLiveData<List<Feature>?>()

    private val _placesList = MutableLiveData<List<PlaceToVisit>?>()
    val placesList: LiveData<List<PlaceToVisit>?>
        get() = _placesList

//    val places = Transformations.map(_features){
//        it?.let {
//            viewModelScope.launch {
//
//                Log.i(TAG, "getPlaces: $places")
//            }
//        }
//        places
//    }

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

    private var isSaved: Boolean? = null

    private val _onNavigateToSeeAllClicked = MutableLiveData<List<Trip>?>()
    val onNavigateToSeeAllClicked: LiveData<List<Trip>?>
        get() = _onNavigateToSeeAllClicked

    init {
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        //getAllTrips()
        getPlaces()
    }

    private fun getPlaces() {
        val places = mutableListOf<PlaceToVisit>()
        viewModelScope.launch {
            try {
                val result = PlacesApiModel.getAllPlaces()
                _features.value = result.features
                for (feature in result.features) {
                    try {
                        val place = PlacesApiModel.getPlace(feature.properties.xid)
                        places.add(place)
                    } catch (t: Throwable) {
                        Log.i(TAG, "getPlaces: ${t.localizedMessage}")
                    }
                }
                _placesList.value = places
            }catch (t: Throwable){
                Log.i(TAG, "getPlaces: ${t.localizedMessage}")
            }
        }
    }

    fun getTripSaveState() = isSaved
    fun getSavedTripsLis() = savedTripsListIDs

    fun getAllTrips() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _allTripsList.value = TripApi.getAllTrips()
                Firebase.auth.currentUser?.let { user ->
                    savedTripsListIDs =
                        TripApi.getAllSavedTripsForUser(user.uid).map { trip ->
                            trip.tripID
                        }
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.GONE
                    _allTripsList.value?.let {
                        if (it.isEmpty()) {
                            _emptyListStatus.value = View.VISIBLE
                        } else {
                            _emptyListStatus.value = View.GONE
                        }
                    }
                }

            } catch (e: Exception) {
                _errorMsg.value = e.localizedMessage
                _errorStatus.value = View.VISIBLE
                _loadingStatus.value = View.GONE
                _emptyListStatus.value = View.GONE
                Log.i(TAG, "getAllTrips: ${e.localizedMessage}")
            }

        }
    }

    fun onNavigateToTripDetails(trip: Trip) {
        isSaved = savedTripsListIDs?.contains(trip.tripID)
        _onNavigateToTripDetailsData.value = trip

    }

    fun onDoneNavigationToTripDetails() {
        _onNavigateToTripDetailsData.value = null
    }

    fun onNavigateToSeeAll() {
        _onNavigateToSeeAllClicked.value = _allTripsList.value
    }

    fun onDoneNavigationToSeeAll() {
        _onNavigateToSeeAllClicked.value = null
    }

}