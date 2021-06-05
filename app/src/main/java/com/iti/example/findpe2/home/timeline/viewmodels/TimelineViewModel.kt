package com.iti.example.findpe2.home.timeline.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.TimelineSlot

class TimelineViewModel(var tripId: Int) : ViewModel() {

    private val _timelineSlotList = MutableLiveData<List<TimelineSlot>?>()
    val timelineSlotList: LiveData<List<TimelineSlot>?>
        get() = _timelineSlotList

    private val _closeActivity = MutableLiveData<Boolean>()
    val closeActivity: LiveData<Boolean>
        get() = _closeActivity


    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg:LiveData<String?>
        get() = _errorMsg

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus:LiveData<Int?>
        get() = _loadingStatus

    private val _errorStatus = MutableLiveData<Int?>()
    val errorStatus:LiveData<Int?>
        get() = _errorStatus

    val emptyListStatus = Transformations.map(timelineSlotList){
        if(it != null) {
            when (it.size) {
                in 1..Int.MAX_VALUE -> View.VISIBLE
                else -> View.GONE
            }
        }else{
            View.VISIBLE
        }

    }

    init {
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE

        getData()
    }

    private fun getData() {

    }
    fun close(){
        _closeActivity.value = true
    }
    fun closeComplete(){
        _closeActivity.value = false
    }

}