package com.iti.example.findpe2.home.companionsList.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.Companion
import kotlinx.coroutines.launch

class CompanionListViewModel : ViewModel() {

    private val _companionList = MutableLiveData<List<Companion>?>()
    val companionList: LiveData<List<Companion>?>
        get() = _companionList

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

    /*private val _isLiked = MutableLiveData<Boolean?>()
    val isLiked: LiveData<Boolean?>
        get() = _isLiked*/

    init {
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        getAllCompanions()

    }

    fun getAllCompanions() {
        _loadingStatus.value = View.VISIBLE
        //get All Companions
        //get Liked Companions by User
        //modify isLiked for each companion based on user liked
        viewModelScope.launch {
            try {
                _companionList.value = TripApi.getAllCompanions()
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.GONE
                if (_companionList.value.isNullOrEmpty()) {
                    _emptyListStatus.value = View.VISIBLE
                } else {
                    _emptyListStatus.value = View.GONE
                }
            } catch (e: Exception) {
                _errorMsg.value = e.localizedMessage
                _loadingStatus.value = View.GONE
                _errorStatus.value = View.VISIBLE
                _emptyListStatus.value = View.GONE
            }
        }


    }

    /*fun onLikeClick(clickedCompanion: Companion) {
        //PUT isLiked on userInfo for selected companion
        //after backend updated
        //update backend
        //when backend done
        _companionList.value = _companionList.value?.map {
            if (it.id == clickedCompanion.id){
                it.isLiked = !clickedCompanion.isLiked
            }
            it
        }
        //_isLiked.value = isLiked


    }*/
    /*fun onDoneLikeClick(){
        _isLiked.value = null
    }*/

}