package com.iti.example.findpe2.companionHolder.levels.amateur.viewmodels

import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.AccountLevel
import com.iti.example.findpe2.pojos.Companion
import com.iti.example.findpe2.pojos.ExpertLevel
import com.iti.example.findpe2.pojos.RegistrationInfo
import kotlinx.coroutines.launch

class AmateurReqViewModel(private val registrationInfo: RegistrationInfo): ViewModel() {
    private val _toastVisibility = MutableLiveData<Boolean?>()
    val toastVisibility: LiveData<Boolean?>
        get() = _toastVisibility

    private val _personalPicUrl = MutableLiveData<String?>()
    val personalPicUrl: LiveData<String?>
        get() = _personalPicUrl

    val personalPicCheckedVisibility = Transformations.map(personalPicUrl){
        if(it != null){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    private val _idUrl = MutableLiveData<String?>()
    val idUrl: LiveData<String?>
        get() = _idUrl

    val idCheckedVisibility = Transformations.map(idUrl){
        if(it != null){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    init {
        _personalPicUrl.value = null
        _idUrl.value = null
    }
    fun toastAppearanceCompleted(){
        _toastVisibility.value = null

    }


    fun setIdUrl(idUrl: String){
        _idUrl.value = idUrl
    }
    fun setProfilePicUrl(profilePicUrl: String){
        _personalPicUrl.value = profilePicUrl
    }
    fun submit(){
        if (personalPicCheckedVisibility.value != View.VISIBLE &&
            idCheckedVisibility.value != View.VISIBLE){
            _toastVisibility.value = true
            return
        }
        val companion = Companion(
            FirebaseAuth.getInstance().currentUser!!.uid,
            ExpertLevel.PROFESSIONAL,
            registrationInfo.city,
            registrationInfo.country,
            "Egyptian",
            "N/A",
            true,
            AccountLevel.BRONZE,
            idUrl.value!!,
            "N/A",
            1
        )
        viewModelScope.launch {
            try {
                TripApi.addANewCompanion(companion)
            }catch (t: Throwable){
            }
        }
    }


}