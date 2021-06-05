package com.iti.example.findpe2.home.profile.views


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemUserGalleryBinding
import com.iti.example.findpe2.pojos.UserGalleryImage

//https://picsum.photos/id/237/200

class UserGalleryAdapter :
    ListAdapter<UserGalleryImage, UserGalleryAdapter.UserGalleryViewHolder>(UserGalleryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserGalleryViewHolder.from(parent)

    override fun onBindViewHolder(holder: UserGalleryViewHolder, position: Int) =
        holder.bind(getItem(position))


    class UserGalleryViewHolder(val binding: ListItemUserGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): UserGalleryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemUserGalleryBinding.inflate(layoutInflater, parent, false)
                return UserGalleryViewHolder(binding)
            }
        }

        fun bind(currentItem: UserGalleryImage) {
            binding.userGalleryImage = currentItem
            binding.executePendingBindings()
        }

    }

    class UserGalleryDiffCallback : DiffUtil.ItemCallback<UserGalleryImage>() {
        override fun areItemsTheSame(oldItem: UserGalleryImage, newItem: UserGalleryImage) =
            oldItem.imageUrl == newItem.imageUrl

        override fun areContentsTheSame(oldItem: UserGalleryImage, newItem: UserGalleryImage) =
            oldItem == newItem
    }

}