package com.iti.example.findpe2.jobHolder.jobdetails.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.constants.Constants
import com.iti.example.findpe2.pojos.*
import kotlinx.coroutines.launch
import java.util.*

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
    val navigateToChatRoom: LiveData<Boolean?>
        get() = _navigateToChatRoom

    private val _navigateToBidFragment = MutableLiveData<Boolean?>()
    val navigateToBidFragment: LiveData<Boolean?>
        get() = _navigateToBidFragment

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus: LiveData<Int?>
        get() = _loadingStatus

    private val _navigateUp = MutableLiveData<Boolean?>()
    val navigateUp: LiveData<Boolean?>
        get() = _navigateUp

    private val _clientID = request?.clientID ?: job?.clientId
    private var companionImageUrl = ""

    val jobBidVisibility = Transformations.map(offerBtnVisibility) {
        it?.let {
            if (it == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private val _snackBarDisplay = MutableLiveData<Boolean?>()
    val snackBarDisplay: LiveData<Boolean?>
        get() = _snackBarDisplay

    init {
        _loadingStatus.value = View.GONE
        _jobDesc.value = request?.desc ?: job?.description ?: ""
        _jobTasks.value = request?.tasks?.get(0) ?: ""
        _jobOffer.value = request?.offer?.toString() ?: job?.jobPrice?.toString() ?: ""
        if (request != null) {
            _offerBtnVisibility.value = View.VISIBLE
        } else {
            _offerBtnVisibility.value = View.GONE
        }
        FirebaseDatabase.getInstance().reference.child("usersImages") //FirebaseAuth.getInstance().currentUser?.photoUrl!!
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnSuccessListener {
                companionImageUrl = it.value.toString()
            }.addOnFailureListener {
                Log.i("JobDetailsVM", it.localizedMessage)
            }

    }

    fun openChatRoom() {
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            val database = FirebaseDatabase.getInstance().reference
            // id -> current companion id
            //request?.clientID -> id of the user who sent the request
            val currentMsgTime = Calendar.getInstance().time
            val companionID = FirebaseAuth.getInstance().currentUser?.uid!!
            val companionName = FirebaseAuth.getInstance().currentUser?.displayName!!
            val companionEmail = FirebaseAuth.getInstance().currentUser?.email
                ?: FirebaseAuth.getInstance().currentUser?.providerData?.get(1)?.email!!



            database.child("ReceivedJobRequests")
                .child(companionID)
                .child(request?.clientID!!)
                .removeValue()
            database.child("SentJobRequests")
                .child(request.clientID!!)
                .child(companionID)
                .setValue(
                    SentJobRequest(
                        companionID,
                        companionName,
                        request.desc,
                        request.offer,
                        request.tasks,
                        RequestStatus.ACCEPTED.value
                    )
                )
            val firestoreDB = Firebase.firestore
            //add user to companion contacts
            firestoreDB
                .collection(companionID)
                .document(request.clientID!!)//userID
                .set(
                    ChatRoom(
                        request.clientID,
                        request.clientName,
                        request.clientImage,
                        Constants.CHAT_ACCEPTANCE_INITIAL_MSG,
                        currentMsgTime
                    )
                ).addOnSuccessListener {
                    //add companion to user contacts
                    firestoreDB.collection(request.clientID)
                        .document(companionID)
                        .set(
                            ChatRoom(
                                companionID,
                                companionName,
                                companionImageUrl.toString(),
                                Constants.CHAT_ACCEPTANCE_INITIAL_MSG,
                                currentMsgTime
                            )
                        ).addOnSuccessListener {
                            //open chat Room
                            val collectionName =
                                if (companionID <= request.clientID) {
                                    companionID + request.clientID
                                } else {
                                    request.clientID + companionID
                                }
                            //add companion first msg to chat
                            firestoreDB.collection(collectionName)
                                .add(
                                    Message(
                                        Constants.CHAT_JOB_INITIAL_MSG,
                                        companionID,
                                        request.clientID,
                                        Constants.REQUEST_SENDER_DUMMY_MAIL,//it should be replaced with user email but it's never used anyway
                                        request.clientImage,
                                        currentMsgTime
                                    )

                                ).addOnSuccessListener {
                                    //add user first msg to chat
                                    firestoreDB.collection(collectionName)
                                        .add(
                                            Message(
                                                Constants.CHAT_ACCEPTANCE_INITIAL_MSG,
                                                request.clientID,
                                                companionID,
                                                companionEmail,
                                                companionImageUrl,
                                                currentMsgTime
                                            )
                                        )
                                    _loadingStatus.value = View.GONE
                                    _navigateToChatRoom.value = true
                                }.addOnFailureListener {
                                    _loadingStatus.value = View.GONE
                                    _navigateToChatRoom.value = false
                                }
                        }
                }
        }
    }

    fun rejectedPressed() {
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

    fun getClientID() = _clientID

    fun openChatRoomCompleted() {
        _navigateToChatRoom.value = null

    }

    fun navigateUpCompleted() {
        _navigateUp.value = null
    }

    fun displayBidFragment() {
        _loadingStatus.value = View.VISIBLE
        val id = FirebaseAuth.getInstance().currentUser?.uid!!
        val database = FirebaseDatabase.getInstance().reference
        database.child("SentBidOffers")
            .child(id)
            .child(job?.jobID.toString()).get()
            .addOnSuccessListener {
                if (it.value == null) {
                    _navigateToBidFragment.value = true
                    _loadingStatus.value = View.GONE

                } else {
                   _snackBarDisplay.value = true
                    _loadingStatus.value = View.GONE
                }
            }
    }

    fun displayBidFragmentCompleted() {
        _navigateToBidFragment.value = null

    }
    fun displaySnackBarComplete(){
        _snackBarDisplay.value = null

    }

}