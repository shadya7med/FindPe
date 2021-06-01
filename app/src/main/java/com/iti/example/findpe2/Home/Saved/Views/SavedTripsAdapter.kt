package com.iti.example.findpe2.home.saved.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemSavedTripBinding
import com.iti.example.findpe2.pojos.Trip

class SavedTripsAdapter :
    ListAdapter<Trip, SavedTripsAdapter.SavedTripViewHolder>(SavedTripDiffCallbacks()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SavedTripViewHolder.from(parent)

    override fun onBindViewHolder(holder: SavedTripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class SavedTripViewHolder(val binding: ListItemSavedTripBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): SavedTripViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSavedTripBinding.inflate(layoutInflater,parent,false)
                return SavedTripViewHolder(binding)
            }
        }
        fun bind(currentTrip:Trip){
            binding.trip = currentTrip
            binding.executePendingBindings()
        }

    }

    class SavedTripDiffCallbacks : DiffUtil.ItemCallback<Trip>() {
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Trip, newItem: Trip) = oldItem == newItem
    }


}