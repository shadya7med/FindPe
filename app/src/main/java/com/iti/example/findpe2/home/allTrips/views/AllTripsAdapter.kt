package com.iti.example.findpe2.home.allTrips.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.TravellingRvItemBinding
import com.iti.example.findpe2.home.travelling.views.OnClickListener
import com.iti.example.findpe2.pojos.Trip

class AllTripsAdapter(private val clickListener: OnClickListener) :ListAdapter<Trip,AllTripsAdapter.AllTripsViewHolder>(AllTripsDiffCallbacks()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTripsViewHolder {
        return AllTripsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AllTripsViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }

    class AllTripsViewHolder(val binding:TravellingRvItemBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup):AllTripsViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TravellingRvItemBinding.inflate(layoutInflater,parent,false)
                return AllTripsViewHolder(binding)
            }
        }
        fun bind(currentTrip:Trip,clickListener:OnClickListener){
            binding.trip = currentTrip
            binding.onClickListener = clickListener
            binding.executePendingBindings()
        }
    }
    class AllTripsDiffCallbacks:DiffUtil.ItemCallback<Trip>(){
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Trip, newItem: Trip) = oldItem == newItem
    }
}