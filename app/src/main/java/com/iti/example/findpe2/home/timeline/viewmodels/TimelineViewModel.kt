package com.iti.example.findpe2.home.timeline.viewmodels

import android.view.View
import androidx.lifecycle.*
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.TimelineSlot
import kotlinx.coroutines.launch

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
                in 1..Int.MAX_VALUE -> View.GONE
                else -> View.VISIBLE
            }
        }else{
            View.GONE
        }

    }

    init {
        _timelineSlotList.value = null
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE

        getData()
    }

    private fun getData() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try {
                _timelineSlotList.value = TripApi.getTimelineSlot(tripId)
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.GONE
            }catch (t: Throwable){
                _errorStatus.value = View.VISIBLE
                _loadingStatus.value = View.GONE
                _errorMsg.value = t.localizedMessage
            }
        }

    }
    fun close(){
        _closeActivity.value = true
    }
    fun closeComplete(){
        _closeActivity.value = false
    }

}