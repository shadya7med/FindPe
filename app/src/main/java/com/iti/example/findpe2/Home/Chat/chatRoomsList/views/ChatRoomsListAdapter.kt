package com.iti.example.findpe2.home.chat.chatRoomsList.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemChatRoomBinding
import com.iti.example.findpe2.pojos.ChatRoom

class ChatRoomsListAdapter :
    ListAdapter<ChatRoom, ChatRoomsListAdapter.ChatRoomsViewHolder>(ChatRoomsDiffCallbacks()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChatRoomsViewHolder.from(parent)

    override fun onBindViewHolder(holder: ChatRoomsViewHolder, position: Int) {

    }

    class ChatRoomsViewHolder(val binding: ListItemChatRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ChatRoomsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemChatRoomBinding.inflate(layoutInflater, parent, false)
                return ChatRoomsViewHolder(binding)
            }
        }
        fun bind(chatRoom:ChatRoom){
            //binding.
        }
    }

    class ChatRoomsDiffCallbacks : DiffUtil.ItemCallback<ChatRoom>() {
        override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom) =
            oldItem.destinationUserID == newItem.destinationUserID

        override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom) = oldItem == newItem
    }


}