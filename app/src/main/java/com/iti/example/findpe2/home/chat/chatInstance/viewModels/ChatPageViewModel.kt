package com.iti.example.findpe2.home.chat.chatInstance.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.pojos.ChatRoom
import com.iti.example.findpe2.pojos.Message
import java.util.*

class ChatPageViewModel(val chatRoom: ChatRoom) : ViewModel() {


    private val _navigateUpToHome = MutableLiveData<Boolean?>()
    val navigateUptoHome: LiveData<Boolean?>
        get() = _navigateUpToHome

    private val _messagesList = MutableLiveData<List<Message>?>()
    val messagesList: LiveData<List<Message>?>
        get() = _messagesList

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?>
        get() = _errorMsg

    private val _onSendMsgSuccess = MutableLiveData<Boolean?>()
    val onSendMsgSuccess: LiveData<Boolean?>
        get() = _onSendMsgSuccess

//    private val _onSendMsgFailed = MutableLiveData<Boolean?>()
//    val onSendMsgFailed:LiveData<Boolean?>
//        get() = _onSendMsgFailed

    init {
        getChatPageMessages()
    }

    fun onNavigateToHome(isNavigating: Boolean) {
        _navigateUpToHome.value = true
    }

    fun onDoneNavigationToHome() {
        _navigateUpToHome.value = null
    }


    private fun getChatPageMessages() {
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            val collectionName =
                if (it.uid <= chatRoom.destinationUserID!!) {
                    it.uid + chatRoom.destinationUserID
                } else {
                    chatRoom.destinationUserID + it.uid
                }
            val db = Firebase.firestore
            val chatRef = db.collection(collectionName)
                .orderBy("time", Query.Direction.ASCENDING)
            chatRef.get()
                .addOnSuccessListener { result ->
                    _messagesList.value = result.toObjects(Message::class.java)
                }
                .addOnFailureListener { e ->
                    _errorMsg.value = e.localizedMessage
                }
            //add listener
            chatRef.addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("ChatPageViewModel", "Listen failed.", e)
                    _errorMsg.value = e.localizedMessage
                    return@addSnapshotListener
                }
                value?.let { result ->
                    _messagesList.value = result.toObjects(Message::class.java)
                }

            }
        }

    }

    fun onSendMsg(msg: String) {
        //update chat room collection
        val currentMsgTime = Calendar.getInstance().time
        val currentUser = Firebase.auth.currentUser
        currentUser?.let { currentUser ->
            val collectionName =
                if (currentUser.uid <= chatRoom.destinationUserID!!) {
                    currentUser.uid + chatRoom.destinationUserID
                } else {
                    chatRoom.destinationUserID + currentUser.uid
                }
            val db = Firebase.firestore
            db.collection(collectionName)
                .add(
                    //1. auto generate for the doc id
                    Message(
                        msg,
                        chatRoom.destinationUserID,
                        currentUser.uid,
                        currentUser.email ?: currentUser.providerData[1].email,
                        currentUser.displayName,
                        currentMsgTime
                    )
                ).addOnSuccessListener {
                    //2. update current user contacts collection
                    db.collection(currentUser.uid)
                        .document(chatRoom.destinationUserID)
                        .set(
                            ChatRoom(
                                chatRoom.destinationUserID,
                                chatRoom.destinationUsername,
                                chatRoom.destinationUserImage,
                                msg,
                                currentMsgTime
                            )
                        ).addOnSuccessListener {
                            //3. update destination contacts collection
                            db.collection(chatRoom.destinationUserID)
                                .document(currentUser.uid)
                                .set(
                                    ChatRoom(
                                        currentUser.uid,
                                        currentUser.displayName,
                                        currentUser.photoUrl.toString(),
                                        msg,
                                        currentMsgTime
                                    )
                                ).addOnSuccessListener {
                                    _onSendMsgSuccess.value = true
                                }.addOnFailureListener {
                                    _onSendMsgSuccess.value = false
                                }
                        }
                }.addOnFailureListener {
                    _onSendMsgSuccess.value = false
                }
        }


    }

    fun onDoneSendingMsg() {
        _onSendMsgSuccess.value = null
    }


}
//val destinationUserID: String?,
//val destinationUsername: String?,
//val destinationUserImage: String?,//it should be String? currently we use a placeholder
//val chatLastMsgText: String?,
//val chatLastMsgTime: Date?

/**
 * db = Firebase.firestore
 * Add users to Collection
 * //add user collection of Contacts
 *currentUser?.let{
 ** db.collection(it.uid)
 *      .document("I9cEcG0u1cP9V9oz7IhP5pZzj5N2")//yehia ID
 *      .set(User(true,"Yehia Azab"))
 *
 * //add the user to each Contact collection
 ** db.collection("I9cEcG0u1cP9V9oz7IhP5pZzj5N2")
 *      .document(currentUser.uid)
 *      .set(User(true,"Shady Ahmed"))
 *}
 *
 * */

/**Add message to chat room collection
 * 1. determine room name based on comparing IDs
//        val collectionName =
//            if (currentUser?.uid!! <= "I9cEcG0u1cP9V9oz7IhP5pZzj5N2") {
//                currentUser?.uid + "I9cEcG0u1cP9V9oz7IhP5pZzj5N2"
//            } else {
//                "I9cEcG0u1cP9V9oz7IhP5pZzj5N2" + currentUser?.uid
//            }

 * 2. add message to the selected room (collection)
// db.collection(collectionName)
//            .add(//auto generate for the doc id
//                Message(
//                    "Hi",
//                    "I9cEcG0u1cP9V9oz7IhP5pZzj5N2",
//                    currentUser?.uid,
//                    currentUser?.email ?: currentUser?.providerData[1].email,
//                    currentUser?.displayName,
//                    Calendar.getInstance().time
//                )
//            )
 *
 * */