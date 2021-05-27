package com.iti.example.findpe2.home

import android.net.Uri
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

    private val _userPhotoUrl:MutableLiveData<Uri> = MutableLiveData()
    val userPhotoUrl:LiveData<Uri>
        get() = _userPhotoUrl

    init{
        _email.value = auth.currentUser!!.email
        _username.value = auth.currentUser!!.displayName
        _userPhotoUrl.value = auth.currentUser!!.photoUrl
    }
    fun logout(){
        auth.signOut()
    }

}