package com.iti.example.findpe2.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.CompanionUser
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {


    var auth: FirebaseAuth = Firebase.auth


    private val _email:MutableLiveData<String> = MutableLiveData()
    val email:LiveData<String>
        get() = _email

    private val _username:MutableLiveData<String> = MutableLiveData()
    val username:LiveData<String>
        get() = _username

    private val _userPhotoUrl:MutableLiveData<String> = MutableLiveData()
    val userPhotoUrl:LiveData<String>
        get() = _userPhotoUrl

    private val _onNavigateToProfile = MutableLiveData<Boolean?>()
    val onNavigateToProfile:LiveData<Boolean?>
        get() = _onNavigateToProfile

    private var isUserAlsoCompanion = false
    private var userAlsoCompanion : CompanionUser? = null

    init{
        _email.value = auth.currentUser!!.email
        _username.value = auth.currentUser!!.displayName
        _userPhotoUrl.value = auth.currentUser!!.photoUrl.toString()
        viewModelScope.launch {
            isUserAlsoCompanion = try{
                userAlsoCompanion = TripApi.getACompanionById(auth.currentUser!!.uid)
                true
            }catch (e:Exception){
                Log.i("HomeVM", e.localizedMessage)
                false
            }

        }
    }

    fun getIsUserAlsoCompanion() = isUserAlsoCompanion
    fun getUserAlsoCompanion() = userAlsoCompanion

    fun onNavigateToProfile(){
        _onNavigateToProfile.value = true
    }
    fun onDoneNavigationToProfile(){
        _onNavigateToProfile.value = null
    }
    fun logout(){
        auth.signOut()
    }

}