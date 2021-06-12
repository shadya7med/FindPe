package com.iti.example.findpe2.home.filter.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.models.TripApi
import kotlinx.coroutines.launch

class FilterViewModel : ViewModel() {

    companion object {
        const val MORE_DAYS_MILLIS = 259200000L
    }


    /*private val _fromDaysList: MutableLiveData<ArrayList<Pair<String, String>>> = MutableLiveData()
    val fromDaysList: LiveData<ArrayList<Pair<String, String>>>
        get() = _fromDaysList

    private val _toDaysList: MutableLiveData<ArrayList<Pair<String, String>>> = MutableLiveData()
    val toDaysList: LiveData<ArrayList<Pair<String, String>>>
        get() = _toDaysList*/

    private val _autoCompleteFromPlacesList = MutableLiveData<List<String>?>()
    val autoCompleteFromPlacesList: LiveData<List<String>?>
        get() = _autoCompleteFromPlacesList

    private val _autoCompleteToPlacesList = MutableLiveData<List<String>?>()
    val autoCompleteToPlacesList: LiveData<List<String>?>
        get() = _autoCompleteToPlacesList

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus: LiveData<Int?>
        get() = _loadingStatus


    private var fromSelectedCity = ""
    private var toSelectedCity = ""

    // STATES are : WIFI, RESTAURANT ,POLL ,INN , PARKING
    private val _featuresStates = MutableLiveData<MutableList<Boolean>?>()
    val featuresStates: LiveData<MutableList<Boolean>?>
        get() = _featuresStates


    private var minPriceRange = 0.0
    private var maxPriceRange = 3500.0


    private val _onNavigateUpData = MutableLiveData<Map<String,Any>?>()
    val onNavigateUpData:LiveData<Map<String,Any>?>
        get() = _onNavigateUpData



    init {
        _featuresStates.value = mutableListOf(false, false, false, false, false)
        /*_fromDaysList.value = arrayListOf()
        _toDaysList.value = arrayListOf()
        //fromDaysList.value?.clear()
        _fromDaysList.value?.addAll(getAWeekCenteredAround(Date().time))
        _toDaysList.value?.addAll(getAWeekCenteredAround(Date().time + MORE_DAYS_MILLIS))
        //Log.i("Filter", "${toDaysList[0]},${toDaysList[1]}")

        //setting initial data to now to lock the calendar
        fromSelectedDateMillis = Date().time*/
        _loadingStatus.value = View.GONE
        getAutoCompletePlaces()

    }

    /*fun getAWeekCenteredAround(dateInMillis: Long): ArrayList<Pair<String, String>> {
        val daysList = ArrayList<Pair<String, String>>()
        val dateFormat = SimpleDateFormat("EEE,dd", Locale.US)
        val cal = Calendar.getInstance()
        cal.timeInMillis = dateInMillis
        cal.add(Calendar.DATE, -3)
        for (index in 0..6) {
            val date = dateFormat.format(cal.time)
            daysList.add("${date[0]}" to date.substring(4))
            cal.add(Calendar.DATE, +1)
        }
        return daysList
    }*/

    /*fun showDatePicker(type: Int): MaterialDatePicker<Long> {
        var datePicker = MaterialDatePicker<Long>()
        when (type) {
            0 -> {
                datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setCalendarConstraints(
                            CalendarConstraints.Builder()
                                .setValidator(DateValidatorPointForward.now())
                                .build()
                        )
                        .build()
                datePicker.addOnPositiveButtonClickListener {
                    fromSelectedDateMillis = it
                    _fromDaysList.value = getAWeekCenteredAround(fromSelectedDateMillis)
                    if (fromSelectedDateMillis > toSelectedDateMillis) {
                        toSelectedDateMillis = fromSelectedDateMillis + MORE_DAYS_MILLIS
                        _toDaysList.value = getAWeekCenteredAround(toSelectedDateMillis)
                    }

                }

            }
            1 -> {
                datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setCalendarConstraints(
                            CalendarConstraints.Builder()
                                .setValidator(DateValidatorPointForward.from(fromSelectedDateMillis))
                                .build()
                        )
                        .build()
                datePicker.addOnPositiveButtonClickListener {
                    toSelectedDateMillis = it
                    _toDaysList.value = getAWeekCenteredAround(toSelectedDateMillis)
                }

            }
        }

        return datePicker

    }*/

    fun setFromSelectedCity(fromCityPosition:Int){
        if (fromCityPosition != -1){
            _autoCompleteFromPlacesList.value?.let {
                fromSelectedCity = it[fromCityPosition]
            }
        }

    }
    fun setToSelectedCity(toCityPosition:Int){
        if (toCityPosition != -1){
            _autoCompleteToPlacesList.value?.let {
                toSelectedCity = it[toCityPosition]
            }
        }

    }

    fun toggleFeaturesState(feature: Int) {
        _featuresStates.value?.let {
            it[feature] =  !it[feature]
            _featuresStates.value = it
        }
    }


    fun storePriceRange(priceRanges: List<Float>) {
        minPriceRange = priceRanges[0].toDouble()
        maxPriceRange = priceRanges[1].toDouble()
    }

    fun getFilterResult(fromCity:String,toCity:String){
        featuresStates.value?.let{
            val resultMap = mutableMapOf<String, Any>()
            resultMap[Keys.FROM_PLACE_KEY] = fromCity//fromSelectedCity
            resultMap[Keys.TO_PLACE_KEY] = toCity//toSelectedCity
            resultMap[Keys.FEATURES_STATES_KEY] = it
            resultMap[Keys.MIN_RANGE_KEY] = minPriceRange
            resultMap[Keys.MAX_RANGE_KEY] = maxPriceRange

            _onNavigateUpData.value = resultMap
        }
    }
    fun onDoneNavigatingUpFromFilter(){
        _onNavigateUpData.value = null
    }

    fun getAutoCompletePlaces() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {

                _autoCompleteFromPlacesList.value = TripApi.getAllFromCities()
                _autoCompleteToPlacesList.value = TripApi.getAllToCities()
                _loadingStatus.value = View.GONE
            } catch (e: Exception) {
                Log.i("Filter", e.localizedMessage)
            } finally {
                _loadingStatus.value = View.GONE
            }

        }
    }


}