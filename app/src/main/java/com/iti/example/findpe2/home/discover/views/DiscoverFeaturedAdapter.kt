package com.iti.example.findpe2.home.discover.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemFeaturedBinding
import com.iti.example.findpe2.databinding.ListItemSavedTripBinding
import com.iti.example.findpe2.home.saved.views.SavedTripsAdapter
import com.iti.example.findpe2.home.travelling.views.OnClickListener
import com.iti.example.findpe2.pojos.Trip

class DiscoverFeaturedAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Trip, DiscoverFeaturedAdapter.DiscoverFeaturedViewHolder>(
        DiscoverFeaturedDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DiscoverFeaturedViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: DiscoverFeaturedAdapter.DiscoverFeaturedViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), clickListener)
    }


    class DiscoverFeaturedViewHolder(val binding: ListItemFeaturedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DiscoverFeaturedViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFeaturedBinding.inflate(layoutInflater, parent, false)
                return DiscoverFeaturedViewHolder(binding)
            }
        }

        fun bind(currentTrip: Trip, clickListener: OnClickListener) {
            binding.trip = currentTrip
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    class DiscoverFeaturedDiffCallback : DiffUtil.ItemCallback<Trip>() {
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip) = oldItem.tripID == newItem.tripID
        override fun areContentsTheSame(oldItem: Trip, newItem: Trip) = oldItem == newItem
    }


}