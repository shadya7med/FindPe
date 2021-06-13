package com.iti.example.findpe2.home.discover.viewsModels

import android.view.View
import androidx.lifecycle.*
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Trip
import kotlinx.coroutines.launch
import kotlin.random.Random

class DiscoverViewModel : ViewModel() {

    private val _allTripsList = MutableLiveData<List<Trip>?>()
    val allTripsList:LiveData<List<Trip>?>
        get() = _allTripsList

    private val _fourRandomTrips = Transformations.map(_allTripsList){ allTrips ->
        allTrips?.let{
            if (it.size > 4){
                val randomTrips = mutableListOf<Trip>()
                val randomIndexes = arrayListOf<Int>()
                for(index in 0..3){
                    var randomInt = Random.nextInt(0,it.size)
                    if (randomIndexes.contains(randomInt)){
                        randomInt = Random.nextInt(0,it.size)
                    }else{
                        randomIndexes.add(randomInt)
                    }
                }
                for(index in  randomIndexes){
                    randomTrips.add(it[index])
                }
                randomTrips
            }else{
                it
            }
        }

    }
    val fourRandomTrips:LiveData<List<Trip>?>
        get() = _fourRandomTrips

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


    private val _onNavigateToSeeAllClicked = MutableLiveData<List<Trip>?>()
    val onNavigateToSeeAllClicked: LiveData<List<Trip>?>
        get() = _onNavigateToSeeAllClicked

    init{
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        getAllTrips()
    }

    private fun getAllTrips(){
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try{
                //should call getAllSaved
                _allTripsList.value = TripApi.getAllTrips()
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.GONE
                _allTripsList.value?.let {
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
        _onNavigateToSeeAllClicked.value = _allTripsList.value
    }
    fun onDoneNavigationToSeeAll(){
        _onNavigateToSeeAllClicked.value = null
    }

}