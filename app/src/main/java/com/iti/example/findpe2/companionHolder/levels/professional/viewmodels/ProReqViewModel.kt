package com.iti.example.findpe2.companionHolder.levels.professional.viewmodels

import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.AccountLevel
import com.iti.example.findpe2.pojos.CompanionUser
import com.iti.example.findpe2.pojos.ExpertLevel
import com.iti.example.findpe2.pojos.RegistrationInfo
import kotlinx.coroutines.launch

class ProReqViewModel(private val registrationInfo: RegistrationInfo): ViewModel() {
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
    private val _censorshipUrl = MutableLiveData<String?>()
    val censorshipUrl: LiveData<String?>
        get() = _censorshipUrl

    val censorshipCheckedVisibility = Transformations.map(censorshipUrl){
        if(it != null){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
    private val _criminalRecordUrl = MutableLiveData<String?>()
    val criminalRecordUrl: LiveData<String?>
        get() = _criminalRecordUrl

    val criminalRecordCheckedVisibility = Transformations.map(criminalRecordUrl){
        if(it != null){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    init {
        _personalPicUrl.value = null
        _idUrl.value = null
        _criminalRecordUrl.value = null
        _censorshipUrl.value = null
    }



    fun setIdUrl(idUrl: String){
        _idUrl.value = idUrl
    }
    fun setProfilePicUrl(profilePicUrl: String){
        _personalPicUrl.value = profilePicUrl
    }
    fun setCensorshipUrl(censorshipUrl: String){
        _censorshipUrl.value = censorshipUrl
    }
    fun setCriminalRecUrl(criminalRecUrl: String){
        _criminalRecordUrl.value = criminalRecUrl
    }
    fun toastAppearanceCompleted(){
        _toastVisibility.value = null

    }

    fun submit(){
        if (personalPicCheckedVisibility.value != View.VISIBLE &&
            idCheckedVisibility.value != View.VISIBLE &&
            censorshipCheckedVisibility.value != View.VISIBLE &&
            criminalRecordCheckedVisibility.value != View.VISIBLE){
            _toastVisibility.value = true
            return
        }
        val companion = CompanionUser(
            FirebaseAuth.getInstance().currentUser!!.uid,
            ExpertLevel.PROFESSIONAL.value,
            registrationInfo.city,
            registrationInfo.country,
            "Egyptian",
            criminalRecordUrl.value!!,
            true,
            AccountLevel.BRONZE.value,
            idUrl.value!!,
            censorshipUrl.value!!,
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