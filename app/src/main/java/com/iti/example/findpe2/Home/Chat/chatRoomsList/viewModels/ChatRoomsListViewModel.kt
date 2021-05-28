package com.iti.example.findpe2.home.chat.chatRoomsList.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.pojos.ChatRoom

class ChatRoomsListViewModel : ViewModel() {

    private val _chatRoomsList = MutableLiveData<List<ChatRoom>?>()
    val chatRoomsList: LiveData<List<ChatRoom>?>
        get() = _chatRoomsList

    private val _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?>
        get() = _errorMsg

    private val _navigateToChatPageData = MutableLiveData<ChatRoom?>()
    val navigateToChatPageData: LiveData<ChatRoom?>
        get() = _navigateToChatPageData

    fun onNavigateToChatPage(chatRoom: ChatRoom) {
        _navigateToChatPageData.value = chatRoom
    }

    fun onDoneNavigateToChatPage() {
        _navigateToChatPageData.value = null
    }

    fun getAllAvailableChatRoom() {
        val db = Firebase.firestore
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            val docsRef = db.collection(it.uid)
                .orderBy("chatLastMsgTime", Query.Direction.DESCENDING)
            docsRef.get()
                .addOnSuccessListener { result ->
                    _chatRoomsList.value = result.toObjects(ChatRoom::class.java)
                }.addOnFailureListener { exception ->
                    _errorMsg.value = exception.localizedMessage
                }
            docsRef.addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("Chat Rooms List", "Listen failed.", e)
                    _errorMsg.value = e.localizedMessage
                    return@addSnapshotListener
                }
                value?.let { result ->
                    _chatRoomsList.value = result.toObjects(ChatRoom::class.java)
                }
            }
        }
    }
}