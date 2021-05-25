package com.iti.example.findpe2.home.filter.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class FilterViewModel : ViewModel() {

    companion object {
        const val MORE_DAYS_MILLIS = 259200000L
        const val FROM_DATE_KEY = "from_date_key"
        const val TO_DATE_KEY = "to_date_key"
        const val FEATURES_STATES_KEY = "features_states_key"
        const val MIN_RANGE_KEY = "min_range_key"
        const val MAX_RANGE_KEY = "max_range_key"
        const val FULL_MAP_KEY = "full_map_key"
    }


    private val _fromDaysList: MutableLiveData<ArrayList<Pair<String, String>>> = MutableLiveData()
    val fromDaysList: LiveData<ArrayList<Pair<String, String>>>
        get() = _fromDaysList

    private val _toDaysList: MutableLiveData<ArrayList<Pair<String, String>>> = MutableLiveData()
    val toDaysList: LiveData<ArrayList<Pair<String, String>>>
        get() = _toDaysList

    // STATES are : WIFI, RESTAURANT ,POLL ,INN , PARKING
    private var featuresStates  = booleanArrayOf(false,false,false,false,false)

    private var fromSelectedDateMillis = 0L
    private var toSelectedDateMillis = 0L

    private var minPriceRange = 0
    private var maxPriceRange = 3500


    init {
        _fromDaysList.value = arrayListOf()
        _toDaysList.value = arrayListOf()
        //fromDaysList.value?.clear()
        _fromDaysList.value?.addAll(getAWeekCenteredAround(Date().time))
        _toDaysList.value?.addAll(getAWeekCenteredAround(Date().time + MORE_DAYS_MILLIS))
        //Log.i("Filter", "${toDaysList[0]},${toDaysList[1]}")

        //setting initial data to now to lock the calendar
        fromSelectedDateMillis = Date().time

    }

    fun getAWeekCenteredAround(dateInMillis: Long): ArrayList<Pair<String, String>> {
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
    }

    fun showDatePicker(type: Int): MaterialDatePicker<Long> {
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

    }

    fun toggleFeaturesState(feature:Int):Boolean{
        featuresStates[feature] = !featuresStates[feature]
        return featuresStates[feature]
    }
    fun storePriceRange(priceRanges:List<Float>){
        minPriceRange = priceRanges[0].toInt()
        maxPriceRange = priceRanges[1].toInt()
    }

    fun getFilterResult():MutableMap<String,Any>{
        val resultMap = mutableMapOf<String,Any>()
        resultMap[FROM_DATE_KEY] = fromSelectedDateMillis
        if (toSelectedDateMillis == 0L){
            //if the user didn't select a new toDate and gone with the default choice
            toSelectedDateMillis = fromSelectedDateMillis + MORE_DAYS_MILLIS
        }
        resultMap[TO_DATE_KEY] = toSelectedDateMillis
        resultMap[FEATURES_STATES_KEY] = featuresStates
        resultMap[MIN_RANGE_KEY] = minPriceRange
        resultMap[MAX_RANGE_KEY] = maxPriceRange

        return resultMap
    }


}