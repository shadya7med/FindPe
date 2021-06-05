package com.iti.example.findpe2.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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

    init{
        _email.value = auth.currentUser!!.email
        _username.value = auth.currentUser!!.displayName
        _userPhotoUrl.value = auth.currentUser!!.photoUrl.toString()
    }
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