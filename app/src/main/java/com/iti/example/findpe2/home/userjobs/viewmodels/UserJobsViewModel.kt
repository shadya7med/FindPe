package com.iti.example.findpe2.home.userjobs.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserJobsViewModel: ViewModel() {
    private val _listOfJobs = MutableLiveData<List<String>?>()
    val listOfJobs: LiveData<List<String>?>
        get() = _listOfJobs

    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility

    init {
        _loadingVisibility.value = View.GONE
    }

    fun getUserJobs() {

        _loadingVisibility.value = View.VISIBLE
        val mDatabase = FirebaseDatabase.getInstance().reference
        val userId = Firebase.auth.currentUser?.uid
        mDatabase.child("ReceivedBidOffers").child(userId!!).get().addOnSuccessListener {
            val response = it.value as Map<String,Map<String, Any?>>?
            val list = mutableListOf<String>()
            response?.forEach { (u,v) ->
                list.add(u)
            }
            _listOfJobs.value = list
            Log.i("firebase", "data${_listOfJobs.value}")
            _loadingVisibility.value = View.GONE
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }
}