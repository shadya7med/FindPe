package com.iti.example.findpe2.home

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel:ViewModel() {

    lateinit var auth: FirebaseAuth

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
        auth = Firebase.auth
        _email.value = auth.currentUser!!.email
        _username.value = auth.currentUser!!.displayName
        _userPhotoUrl.value = auth.currentUser!!.photoUrl
    }

}