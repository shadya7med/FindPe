package com.iti.example.findpe.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {

    val email:MutableLiveData<String> = MutableLiveData()
    init{
        email.value = "sahedyahmed16@gmail.com"
    }

}