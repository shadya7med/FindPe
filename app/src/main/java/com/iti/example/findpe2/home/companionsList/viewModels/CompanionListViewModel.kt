package com.iti.example.findpe2.home.companionsList.viewModels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.AccountLevel
import com.iti.example.findpe2.pojos.Companion

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

    private fun getAllCompanions() {
        _loadingStatus.value = View.VISIBLE
        //get All Companions
        //get Liked Companions by User
        //modify isLiked for each companion based on user liked

        //for testing
        _companionList.value = listOf(
            Companion(
                "56879643",
                "Lina Ahmed Adel",
                "",
                false,
                AccountLevel.BRONZE,
                "Amateur Companion",
                "sakdjaskldjaskdkjfhakjlsdfhsjkfkslfd"
            ),
            Companion(
                "56879698",
                "Lina Ali Adel",
                "",
                true,
                AccountLevel.SILVER,
                "intermediate Companion",
                "sakdjaskldjaskdkjfhakjlsdfhsjkfkslfd"
            ),
            Companion(
                "56879643",
                "MO Ahmed Adel",
                "",
                false,
                AccountLevel.GOLD,
                "Professional Companion",
                "sakdjaskldjaskdkjfhakjlsdfhsjkfkslfd"
            ),
            Companion(
                "231354688",
                "Amy Ahmed Saeed",
                "",
                false,
                AccountLevel.SILVER,
                "Amateur Companion",
                "sakdjaskldjaskdkjfhakjlsdfhsjkfkslfd"
            ),
            Companion(
                "213456231",
                "John Bjorn Adel",
                "",
                true,
                AccountLevel.BRONZE,
                "Amateur Companion",
                "sakdjaskldjaskdkjfhakjlsdfhsjkfkslfd"
            ),
            Companion(
                "635265987",
                "Lina Ahmed Adel",
                "",
                false,
                AccountLevel.GOLD,
                "Amateur Companion",
                "sakdjaskldjaskdkjfhakjlsdfhsjkfkslfd"
            )
        )

        _loadingStatus.value = View.GONE
    }

    fun onLikeClick(clickedCompanion: Companion) {
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


    }
    /*fun onDoneLikeClick(){
        _isLiked.value = null
    }*/

}