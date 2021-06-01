package com.iti.example.findpe2.home.chat.chatInstance.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.pojos.ChatRoom

class ChatPageViewModelFactory(val chatRoom: ChatRoom) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatPageViewModel(chatRoom) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
