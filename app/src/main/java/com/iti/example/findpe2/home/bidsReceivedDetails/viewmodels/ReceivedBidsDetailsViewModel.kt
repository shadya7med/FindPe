package com.iti.example.findpe2.home.bidsReceivedDetails.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.constants.Constants
import com.iti.example.findpe2.pojos.*
import kotlinx.coroutines.launch
import java.util.*

class ReceivedBidsDetailsViewModel (private val receivedBid: ReceivedBid) :
    ViewModel() {

    private val _bidProposal = MutableLiveData<String?>()
    val bidProposal: LiveData<String?>
        get() = _bidProposal

    private val _bidOffer = MutableLiveData<String?>()
    val bidOffer: LiveData<String?>
        get() = _bidOffer

    private val _navigateToChatRoom = MutableLiveData<Boolean?>()
    val navigateToChatRoom: LiveData<Boolean?>
        get() = _navigateToChatRoom


    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus: LiveData<Int?>
        get() = _loadingStatus

    private val _navigateUp = MutableLiveData<Boolean?>()
    val navigateUp: LiveData<Boolean?>
        get() = _navigateUp


    private val _snackBarDisplay = MutableLiveData<Boolean?>()
    val snackBarDisplay: LiveData<Boolean?>
        get() = _snackBarDisplay
    private var clientImage = ""

    init {
        _loadingStatus.value = View.GONE
        _bidProposal.value = receivedBid.proposal
        _bidOffer.value = receivedBid.offer.toString()

        FirebaseDatabase.getInstance().reference.child("usersImages") //FirebaseAuth.getInstance().currentUser?.photoUrl!!
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnSuccessListener {
                clientImage = it.value.toString()
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
            val clientID = FirebaseAuth.getInstance().currentUser?.uid!!
            val clientName = FirebaseAuth.getInstance().currentUser?.displayName!!
            val clientEmail = FirebaseAuth.getInstance().currentUser?.email
                ?: FirebaseAuth.getInstance().currentUser?.providerData?.get(1)?.email!!


            database.child("ReceivedBidOffers")
                .child(clientID)
                .child(receivedBid.jobID.toString())
                .child(receivedBid.companionID!!)
                .removeValue()
            database.child("SentBidOffers")
                .child(receivedBid.companionID)
                .child(receivedBid.jobID.toString())
                .setValue(
                    SentBid(
                        receivedBid.jobID,
                        receivedBid.clientID,
                        clientName,
                        receivedBid.proposal,
                        receivedBid.offer,
                        RequestStatus.ACCEPTED.value
                    )
                )
            val firestoreDB = Firebase.firestore
            //add user to companion contacts
            firestoreDB
                .collection(receivedBid.companionID)
                .document(clientID)//userID
                .set(
                    ChatRoom(
                        clientID,
                        clientName,
                        clientImage,
                        Constants.CHAT_ACCEPTANCE_INITIAL_MSG,
                        currentMsgTime

                    )
                ).addOnSuccessListener {
                    //add companion to user contacts
                    firestoreDB.collection(clientID)
                        .document(receivedBid.companionID)
                        .set(
                            ChatRoom(
                                receivedBid.companionID,
                                receivedBid.companionName,
                                receivedBid.companionImage.toString(),
                                Constants.CHAT_ACCEPTANCE_INITIAL_MSG,
                                currentMsgTime
                            )
                        ).addOnSuccessListener {
                            //open chat Room
                            val collectionName =
                                if (receivedBid.companionID <= clientID) {
                                    receivedBid.companionID + clientID!!
                                } else {
                                    clientID + receivedBid.companionID
                                }
                            //add companion first msg to chat
                            firestoreDB.collection(collectionName)
                                .add(
                                    Message(
                                        Constants.CHAT_BID_INITIAL_MSG,
                                        clientID,
                                        receivedBid.companionID,
                                        Constants.REQUEST_SENDER_DUMMY_MAIL,//it should be replaced with user email but it's never used anyway
                                        receivedBid.companionName,
                                        currentMsgTime
                                    )

                                ).addOnSuccessListener {
                                    //add user first msg to chat
                                    firestoreDB.collection(collectionName)
                                        .add(
                                            Message(
                                                Constants.CHAT_ACCEPTANCE_INITIAL_MSG,
                                                receivedBid.companionID,
                                                clientID,
                                                clientEmail,
                                                clientImage,
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
        val clientID = FirebaseAuth.getInstance().currentUser?.uid!!
        val clientName = FirebaseAuth.getInstance().currentUser?.displayName!!
        val database = FirebaseDatabase.getInstance().reference
        database.child("ReceivedBidOffers")
            .child(clientID)
            .child(receivedBid.jobID.toString())
            .child(receivedBid.companionID!!)
            .removeValue()
        database.child("SentBidOffers")
            .child(receivedBid.companionID!!)
            .child(receivedBid.jobID.toString())
            .setValue(
                SentBid(
                    receivedBid.jobID,
                    receivedBid.clientID,
                    clientName,
                    receivedBid.proposal,
                    receivedBid.offer,
                    RequestStatus.REJECTED.value
                )
            )
        _navigateUp.value = true
    }

//    fun getClientID() = _clientID

    fun openChatRoomCompleted() {
        _navigateToChatRoom.value = null

    }

    fun navigateUpCompleted() {
        _navigateUp.value = null
    }

    fun displaySnackBarComplete(){
        _snackBarDisplay.value = null

    }


}