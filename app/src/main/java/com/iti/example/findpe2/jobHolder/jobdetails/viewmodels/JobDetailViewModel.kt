package com.iti.example.findpe2.jobHolder.jobdetails.viewmodels

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.iti.example.findpe2.pojos.Job
import com.iti.example.findpe2.pojos.ReceivedJobRequest
import com.iti.example.findpe2.pojos.RequestStatus
import com.iti.example.findpe2.pojos.SentJobRequest
import kotlinx.coroutines.launch

class JobDetailViewModel(private val request: ReceivedJobRequest?, private val job: Job?) :
    ViewModel() {

    private val _jobDesc = MutableLiveData<String?>()
    val jobDesc: LiveData<String?>
        get() = _jobDesc

    private val _jobTasks = MutableLiveData<String?>()
    val jobTasks: LiveData<String?>
        get() = _jobTasks

    private val _jobOffer = MutableLiveData<String?>()
    val jobOffer: LiveData<String?>
        get() = _jobOffer

    private val _jobStatus = MutableLiveData<String?>()
    val jobStatus: LiveData<String?>
        get() = _jobStatus

    private val _offerBtnVisibility = MutableLiveData<Int?>()
    val offerBtnVisibility: LiveData<Int?>
        get() = _offerBtnVisibility

    private val _navigateToChatRoom = MutableLiveData<Boolean?>()
    val navigateToChatRoom:LiveData<Boolean?>
        get() = _navigateToChatRoom

    private val _navigateUp = MutableLiveData<Boolean?>()
    val navigateUp:LiveData<Boolean?>
        get() = _navigateUp


    init {
        _jobDesc.value = request?.desc ?: job?.description ?: ""
        _jobTasks.value = request?.tasks?.get(0) ?: ""
        _jobOffer.value = request?.offer?.toString() ?: job?.jobPrice?.toString() ?: ""
        if(request != null){
            _offerBtnVisibility.value = View.VISIBLE
        }
    }

    fun openChatRoom(){
        viewModelScope.launch {
            val id = FirebaseAuth.getInstance().currentUser?.uid!!
            val name = FirebaseAuth.getInstance().currentUser?.displayName!!
            val database = FirebaseDatabase.getInstance().reference
            database.child("ReceivedJobRequests")
                .child(id)
                .child(request?.clientID!!)
                .removeValue()
            database.child("SentJobRequests")
                .child(request?.clientID!!)
                .child(id)
                .setValue(
                    SentJobRequest(
                        id,
                        name,
                        request.desc,
                        request.offer,
                        request.tasks,
                        RequestStatus.ACCEPTED.value
                    )
                )

            _navigateToChatRoom.value = true
        }
    }
    fun rejectedPressed(){
        val id = FirebaseAuth.getInstance().currentUser?.uid!!
        val name = FirebaseAuth.getInstance().currentUser?.displayName!!
        val database = FirebaseDatabase.getInstance().reference
        database.child("ReceivedJobRequests")
            .child(id)
            .child(request?.clientID!!)
            .removeValue()
        database.child("SentJobRequests")
            .child(request?.clientID!!)
            .child(id)
            .setValue(
                SentJobRequest(
                    id,
                    name,
                    request.desc,
                    request.offer,
                    request.tasks,
                    RequestStatus.REJECTED.value
                )
            )
        _navigateUp.value = true
    }

    fun openChatRoomCompleted(){
        _navigateToChatRoom.value = null

    }
    fun navigateUpCompleted(){
        _navigateUp.value = null
    }

}