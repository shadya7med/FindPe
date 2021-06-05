package com.iti.example.findpe2.home.profile.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemUserInfoBinding
import com.iti.example.findpe2.pojos.UserInfo

class UserInfoAdapter :
    ListAdapter<UserInfo, UserInfoAdapter.UserInfoViewHolder>(UserInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserInfoViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) =
        holder.bind(getItem(position))

    class UserInfoViewHolder(val binding: ListItemUserInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): UserInfoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemUserInfoBinding.inflate(layoutInflater, parent, false)
                return UserInfoViewHolder(binding)
            }
        }

        fun bind(currentItem: UserInfo) {
            binding.userInfo = currentItem
            binding.executePendingBindings()
        }

    }

    class UserInfoDiffCallback : DiffUtil.ItemCallback<UserInfo>() {
        override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo) =
            oldItem.title.value == newItem.title.value

        override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo) = oldItem == newItem
    }


}
