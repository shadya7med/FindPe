package com.iti.example.findpe2.home.saved.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemSavedTripBinding
import com.iti.example.findpe2.pojos.Trip

class SavedTripsAdapter(private val clickListener: SavedTripsClickListener) :
    ListAdapter<Trip, SavedTripsAdapter.SavedTripViewHolder>(SavedTripDiffCallbacks()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SavedTripViewHolder.from(parent)

    override fun onBindViewHolder(holder: SavedTripViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
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
        fun bind(currentTrip:Trip,clickListener: SavedTripsClickListener){
            binding.trip = currentTrip
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    class SavedTripDiffCallbacks : DiffUtil.ItemCallback<Trip>() {
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip) = oldItem.tripID == newItem.tripID
        override fun areContentsTheSame(oldItem: Trip, newItem: Trip) = oldItem == newItem
    }

    class SavedTripsClickListener(private val clickListener: (trip:Trip)->Unit){
        fun onClick(trip:Trip) = clickListener(trip)
    }


}