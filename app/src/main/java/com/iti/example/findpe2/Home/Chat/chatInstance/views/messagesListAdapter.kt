package com.iti.example.findpe2.home.chat.chatInstance.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemReceivedMessageBinding
import com.iti.example.findpe2.databinding.ListItemSentMessageBinding
import com.iti.example.findpe2.pojos.Message

enum class ViewType(val value: Int) {
    VIEW_TYPE_SENT(0),
    VIEW_TYPE_RECEIVED(1)
}

class MessagesListAdapter(private val userId: String) :
    ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ViewType.VIEW_TYPE_SENT.value) {
            SentMessageViewHolder.from(parent)
        } else {
            ReceivedMessageViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position).senderID == userId) {
            (holder as SentMessageViewHolder).bind(getItem(position))
        } else {
            (holder as ReceivedMessageViewHolder).bind(getItem(position))
        }

    }

    override fun getItemViewType(position: Int) =
        if (getItem(position).senderID == userId) ViewType.VIEW_TYPE_SENT.value else ViewType.VIEW_TYPE_RECEIVED.value


    class SentMessageViewHolder private constructor(val binding: ListItemSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SentMessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSentMessageBinding.inflate(layoutInflater, parent, false)
                return SentMessageViewHolder(binding)
            }
        }

        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
        }

    }

    class ReceivedMessageViewHolder private constructor(val binding: ListItemReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ReceivedMessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemReceivedMessageBinding.inflate(layoutInflater, parent, false)
                return ReceivedMessageViewHolder(binding)
            }
        }

        fun bind(message: Message) {
            binding.message = message
            binding.executePendingBindings()
        }

    }

    class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) =
            oldItem.time == newItem.time

        override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem

    }


}