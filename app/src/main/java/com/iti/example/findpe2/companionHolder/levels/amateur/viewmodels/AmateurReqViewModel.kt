package com.iti.example.findpe2.companionHolder.levels.amateur.viewmodels

import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.AccountLevel
import com.iti.example.findpe2.pojos.CompanionUser
import com.iti.example.findpe2.pojos.ExpertLevel
import com.iti.example.findpe2.pojos.RegistrationInfo
import kotlinx.coroutines.launch

class AmateurReqViewModel(private val registrationInfo: RegistrationInfo) : ViewModel() {
    private val _snackbarEvent = MutableLiveData<Boolean?>()
    val snackbarEvent: LiveData<Boolean?>
        get() = _snackbarEvent

    private val _finishEvent = MutableLiveData<Boolean?>()
    val finishEvent: LiveData<Boolean?>
        get() = _finishEvent

    private val _personalPicUrl = MutableLiveData<String?>()
    val personalPicUrl: LiveData<String?>
        get() = _personalPicUrl

    val personalPicCheckedVisibility = Transformations.map(personalPicUrl) {
        if (it != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private val _idUrl = MutableLiveData<String?>()
    val idUrl: LiveData<String?>
        get() = _idUrl

    val idCheckedVisibility = Transformations.map(idUrl) {
        if (it != null) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility

    init {
        _loadingVisibility.value = View.GONE
        _personalPicUrl.value = null
        _idUrl.value = null
    }

    fun snackbarAppearanceCompleted() {
        _snackbarEvent.value = null

    }


    fun setIdUrl(idUrl: String) {
        _idUrl.value = idUrl
    }

    fun setProfilePicUrl(profilePicUrl: String) {
        _personalPicUrl.value = profilePicUrl
    }

    fun submit() {
        if (personalPicCheckedVisibility.value != View.VISIBLE ||
            idCheckedVisibility.value != View.VISIBLE
        ) {
            _snackbarEvent.value = true
        } else {
            val companion = CompanionUser(
                FirebaseAuth.getInstance().currentUser!!.uid,
                ExpertLevel.PROFESSIONAL.value,
                registrationInfo.city,
                registrationInfo.country,
                "Egyptian",
                "N/A",
                true,
                AccountLevel.BRONZE.value,
                idUrl.value!!,
                "N/A",
                1
            )
            viewModelScope.launch {
                try {
                    TripApi.addANewCompanion(companion)
                } catch (t: Throwable) {

                }
            }
        }
    }

    private fun finishActivity() {
        _finishEvent.value = true
    }

}