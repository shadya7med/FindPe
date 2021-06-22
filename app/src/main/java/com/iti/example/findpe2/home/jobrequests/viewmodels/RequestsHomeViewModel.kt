package com.iti.example.findpe2.home.jobrequests.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.pojos.ReceivedJobRequest

class RequestsHomeViewModel() : ViewModel() {
    private val _listOfRequest = MutableLiveData<List<ReceivedJobRequest>?>()
    val listOfRequest: LiveData<List<ReceivedJobRequest>?>
        get() = _listOfRequest

    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility

    init {
        _loadingVisibility.value = View.GONE
//        _listOfRequest.value = mutableListOf()
//        getRequests()
    }

    fun getRequests() {
        _loadingVisibility.value = View.VISIBLE

        val mDatabase = FirebaseDatabase.getInstance().reference
        val companionId = Firebase.auth.currentUser?.uid
        mDatabase.child("ReceivedJobRequests").child(companionId!!).get().addOnSuccessListener {
            val response = it.value as Map<String, Map<String, Any?>>?
            val list = mutableListOf<ReceivedJobRequest>()
            response?.forEach { (t, u) ->
                val request = ReceivedJobRequest(
                    u["clientID"] as String?,
                    u["clientName"] as String?,
                    u["clientImage"] as String?,
                    u["desc"] as String?,
                    u["offer"] as Long?,
                    u["tasks"] as List<String?>?,
                )
                list.add(request)
            }
            _listOfRequest.value = list
            Log.i("firebase", "data${_listOfRequest.value}")
            _loadingVisibility.value = View.GONE
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }
}