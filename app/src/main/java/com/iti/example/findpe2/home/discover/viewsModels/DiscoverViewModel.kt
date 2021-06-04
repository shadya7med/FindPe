package com.iti.example.findpe2.home.discover.viewsModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch

class DiscoverViewModel : ViewModel() {

    private val _featuredTripList = MutableLiveData<List<Trip>?>()
    val featuredTripList:LiveData<List<Trip>?>
        get() = _featuredTripList

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
    val onNavigateToTripDetailsData:LiveData<Trip?>
        get() = _onNavigateToTripDetailsData


    private val _onNavigateToSeeAllClicked = MutableLiveData<Boolean?>()
    val onNavigateToSeeAllClicked: LiveData<Boolean?>
        get() = _onNavigateToSeeAllClicked

    init{
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        getAllFeaturedTrips()
    }

    private fun getAllFeaturedTrips(){
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try{
                //should call getAllSaved
                _featuredTripList.value = TripApi.getAllFeaturedTrips()
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.GONE
                _featuredTripList.value?.let {
                    if (it.isEmpty()){
                        _emptyListStatus.value = View.VISIBLE
                    }else{
                        _emptyListStatus.value = View.GONE
                    }
                }
            }catch (e:Exception){
                _errorMsg.value = e.localizedMessage
                _errorStatus.value = View.VISIBLE
                _loadingStatus.value = View.GONE
                _emptyListStatus.value = View.GONE

            }

        }
    }

    fun onNavigateToTripDetails(trip:Trip){
        _onNavigateToTripDetailsData.value = trip
    }
    fun onDoneNavigationToTripDetails(){
        _onNavigateToTripDetailsData.value = null
    }

    fun onNavigateToSeeAll(){
        _onNavigateToSeeAllClicked.value = true
    }
    fun onDoneNavigationToSeeAll(){
        _onNavigateToSeeAllClicked.value = null
    }

}