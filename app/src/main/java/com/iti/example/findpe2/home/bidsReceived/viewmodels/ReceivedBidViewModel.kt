package com.iti.example.findpe2.home.bidsReceived.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.pojos.ReceivedBid

class ReceivedBidViewModel(private val jobId: Int): ViewModel(){
    private val _listOfReceivedBid = MutableLiveData<List<ReceivedBid>?>()
    val listOfReceivedBid: LiveData<List<ReceivedBid>?>
        get() = _listOfReceivedBid

    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility

    init {
        _loadingVisibility.value = View.GONE
    }

    fun getRequests() {

        _loadingVisibility.value = View.VISIBLE
        val mDatabase = FirebaseDatabase.getInstance().reference
        val userId = Firebase.auth.currentUser?.uid
        mDatabase.child("ReceivedBidOffers").child(userId!!).child(jobId.toString()).get().addOnSuccessListener {
            val response = it.value as Map<String, Map<String, Map<String, Any?>>>?
            val list = mutableListOf<ReceivedBid>()
            response?.forEach { (t, u) ->
                val request = ReceivedBid(
                    u["companionID"] as String?,
                    u["clientID"] as String?,
                    u["companionImage"] as String?,
                    u["companionName"] as String?,
                    u["jobID"] as Long?,
                    u["proposal"] as String?,
                    u["offer"] as Long?,
                )
                list.add(request)
            }
            _listOfReceivedBid.value = list
            Log.i("firebase", "data${_listOfReceivedBid.value}")
            _loadingVisibility.value = View.GONE
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }
}