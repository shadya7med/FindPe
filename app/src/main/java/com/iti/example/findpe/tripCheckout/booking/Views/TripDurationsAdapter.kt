package com.iti.example.findpe.tripCheckout.booking.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe.databinding.ListItemTripDurationBinding
import com.iti.example.findpe.pojos.TripInfo

class TripDurationsAdapter :
    ListAdapter<TripInfo, TripDurationsAdapter.TripDurationViewHolder>(TripDurationItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TripDurationViewHolder.from(parent)

    override fun onBindViewHolder(holder: TripDurationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TripDurationViewHolder private constructor(val binding: ListItemTripDurationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup):TripDurationViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTripDurationBinding.inflate(layoutInflater,parent,false)
                return TripDurationViewHolder(binding)
            }
        }
        fun bind(currentItem:TripInfo){
            binding.tripInfo = currentItem
            // A MUST when using data binding with Adapter
            binding.executePendingBindings()
        }
    }

    class TripDurationItemCallback : DiffUtil.ItemCallback<TripInfo>() {
        override fun areItemsTheSame(oldItem: TripInfo, newItem: TripInfo) =
            oldItem.tripId == newItem.tripId

        override fun areContentsTheSame(oldItem: TripInfo, newItem: TripInfo) = oldItem == newItem
    }
}