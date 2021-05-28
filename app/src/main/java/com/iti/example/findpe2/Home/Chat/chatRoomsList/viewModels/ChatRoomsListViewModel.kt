package com.iti.example.findpe2.home.chat.chatRoomsList.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.ChatRoom

class ChatRoomsListViewModel:ViewModel() {

    /*private val _chatRoom = MutableLiveData<ChatRoom>()
    val chatRoom:LiveData<ChatRoom>
        get() = _chatRoom*/

    private val _navigateToChatPageData = MutableLiveData<ChatRoom?>()
    val navigateToChatPageData:LiveData<ChatRoom?>
        get() = _navigateToChatPageData

    fun onNavigateToChatPage(chatRoom: ChatRoom){
        _navigateToChatPageData.value = chatRoom
    }

    fun onDoneNavigateToChatPage(){
        _navigateToChatPageData.value = null
    }
}