package com.iti.example.findpe2.tripCheckout.booking.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemNumOfSeatsBinding
import com.iti.example.findpe2.pojos.TripInfo

class NumOfSeatsAdapter :ListAdapter<TripInfo,NumOfSeatsAdapter.NumOfSeatsViewHolder>(NumOfSeatsCallbacks()){


    //inflate + refer --> from using binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NumOfSeatsViewHolder.from(parent)

    override fun onBindViewHolder(holder: NumOfSeatsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NumOfSeatsViewHolder(val binding: ListItemNumOfSeatsBinding) : RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup):NumOfSeatsViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNumOfSeatsBinding.inflate(layoutInflater,parent,false)
                return NumOfSeatsViewHolder(binding)
            }
        }
        fun bind(currentTripInfo:TripInfo){
            binding.tripInfo = currentTripInfo
            binding.executePendingBindings()
        }
    }
    //DiffUtil Callbacks used by ListAdapter for comparing
    class NumOfSeatsCallbacks:DiffUtil.ItemCallback<TripInfo>(){
        override fun areItemsTheSame(oldItem: TripInfo, newItem: TripInfo) = oldItem.tripId == newItem.tripId
        override fun areContentsTheSame(oldItem: TripInfo, newItem: TripInfo) = oldItem == newItem
    }
}