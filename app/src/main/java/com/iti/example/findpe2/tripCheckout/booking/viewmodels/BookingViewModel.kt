package com.iti.example.findpe2.tripCheckout.booking.viewmodels

import android.view.View
import androidx.lifecycle.*
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.TripDuration
import kotlinx.coroutines.launch

class BookingViewModel(var tripId: Int) : ViewModel() {

    private val _navigateToCheckout = MutableLiveData<Pair<TripDuration, Int>?>()
    val navigateToCheckout: LiveData<Pair<TripDuration, Int>?>
        get() = _navigateToCheckout

    private val  _selectedDuration = MutableLiveData<TripDuration?>()
    val selectedDuration: LiveData<TripDuration?>
        get() = _selectedDuration

    val numberOfSeats = Transformations.map(selectedDuration){
        it?.noOfSeets ?: 0
    }

    private val _listOfDurations = MutableLiveData<List<TripDuration>?>()
    val listOfDurations: LiveData<List<TripDuration>?>
        get() = _listOfDurations

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?>
        get() = _errorMsg

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus: LiveData<Int?>
        get() = _loadingStatus

    private val _errorStatus = MutableLiveData<Int?>()
    val errorStatus: LiveData<Int?>
        get() = _errorStatus

    val emptyListStatus = Transformations.map(listOfDurations) {
        if (it != null) {
            when (it.size) {
                in 1..Int.MAX_VALUE -> View.GONE
                else -> View.VISIBLE
            }
        } else {
            View.VISIBLE
        }
    }
    val numberPickerVisibility = Transformations.map(numberOfSeats) {
        if (it != null) {
            when (it) {
                in 1..Int.MAX_VALUE -> View.VISIBLE
                else -> View.GONE
            }
        } else {
            View.GONE
        }
    }

    init {
        _selectedDuration.value = null
        _loadingStatus.value = View.GONE
        _errorStatus.value = View.GONE
        getDurations()
    }

    private fun getDurations() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _listOfDurations.value = TripApi.getTripDurations(tripId)
                _loadingStatus.value = View.GONE
            } catch (t: Throwable) {
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.VISIBLE
                _errorMsg.value = t.localizedMessage
            }
        }
    }
    fun setSelectedDuration(tripDuration: TripDuration){
        if(_selectedDuration.value == tripDuration){
            _selectedDuration.value = null
        }else{
            _selectedDuration.value = tripDuration
        }
    }
    fun displayCheckout(selectedTripDuration: TripDuration, noOfSeats: Int){
        _navigateToCheckout.value = selectedTripDuration to noOfSeats
    }
    fun displayCheckoutComplete(){
        _navigateToCheckout.value = null

    }
}