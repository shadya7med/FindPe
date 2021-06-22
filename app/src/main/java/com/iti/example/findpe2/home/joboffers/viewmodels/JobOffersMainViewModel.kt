package com.iti.example.findpe2.home.joboffers.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.pojos.SentJobRequest

class JobOffersMainViewModel: ViewModel(){
    private val _listOfRequest = MutableLiveData<List<SentJobRequest>?>()
    val listOfRequest: LiveData<List<SentJobRequest>?>
        get() = _listOfRequest

    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility

    init {
        _loadingVisibility.value = View.GONE
//        getRequests()
    }

    fun getRequests() {

        _loadingVisibility.value = View.VISIBLE
        val mDatabase = FirebaseDatabase.getInstance().reference
        val userId = Firebase.auth.currentUser?.uid
        mDatabase.child("SentJobRequests").child(userId!!).get().addOnSuccessListener {
            val response = it.value as Map<String, Map<String, Any?>>?
            val list = mutableListOf<SentJobRequest>()
            response?.forEach { (t, u) ->
                val request = SentJobRequest(
                    u["companionID"] as String?,
                    u["name"] as String?,
                    u["desc"] as String?,
                    u["offer"] as Long?,
                    u["tasks"] as List<String?>?,
                    u["statue"] as String?
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