package com.iti.example.findpe2.home.chat.chatRoomsList.viewModels

import android.util.Log
import android.view.View
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

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus:LiveData<Int?>
        get() = _loadingStatus

    private val _errorStatus = MutableLiveData<Int?>()
    val errorStatus:LiveData<Int?>
        get() = _errorStatus

    private val _emptyListStatus = MutableLiveData<Int?>()
    val emptyListStatus:LiveData<Int?>
        get() = _emptyListStatus


    init{
        _emptyListStatus.value = View.GONE
        _errorStatus.value = View.GONE
        _loadingStatus.value = View.GONE
        getAllAvailableChatRoom()
    }

    fun onNavigateToChatPage(chatRoom: ChatRoom) {
        _navigateToChatPageData.value = chatRoom
    }

    fun onDoneNavigateToChatPage() {
        _navigateToChatPageData.value = null
    }

    private fun getAllAvailableChatRoom() {
        val db = Firebase.firestore
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            _loadingStatus.value = View.VISIBLE
            val docsRef = db.collection(it.uid)
                .orderBy("chatLastMsgTime", Query.Direction.DESCENDING)
            docsRef.get()
                .addOnSuccessListener { result ->
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.GONE
                    _chatRoomsList.value = result.toObjects(ChatRoom::class.java)
                    if(result.isEmpty){
                        _emptyListStatus.value = View.VISIBLE
                    }else{
                        _emptyListStatus.value = View.GONE
                    }
                }.addOnFailureListener { exception ->
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.VISIBLE
                    _errorMsg.value = exception.localizedMessage
                }
            docsRef.addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("Chat Rooms List", "Listen failed.", e)
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.VISIBLE
                    _errorMsg.value = e.localizedMessage
                    return@addSnapshotListener
                }
                value?.let { result ->
                    _loadingStatus.value = View.GONE
                    _errorStatus.value = View.GONE
                    _chatRoomsList.value = result.toObjects(ChatRoom::class.java)
                    if(result.isEmpty){
                        _emptyListStatus.value = View.VISIBLE
                    }else{
                        _emptyListStatus.value = View.GONE
                    }
                }
            }
        }
    }
}