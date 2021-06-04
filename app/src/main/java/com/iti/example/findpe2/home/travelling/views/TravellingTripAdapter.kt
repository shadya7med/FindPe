package com.iti.example.findpe2.home.travelling.views

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemTravellingRvBinding
import com.iti.example.findpe2.pojos.Trip

private const val TAG = "TravellingTripAdapter"
class TravellingTripAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Trip, TravellingTripAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Trip>() {
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip) = oldItem === newItem

        override fun areContentsTheSame(oldItem: Trip, newItem: Trip) = oldItem.id == newItem.id
    }) {
    class ViewHolder(var binding: ListItemTravellingRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip, clickListener: OnClickListener) {
            Log.i(TAG, "bind: $trip")
            binding.trip = trip
            binding.onClickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ListItemTravellingRvBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)
}
class OnClickListener(var clickListener: (Trip) -> Unit){
    fun onClick(trip: Trip) = clickListener(trip)
}