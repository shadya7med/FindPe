package com.iti.example.findpe2.tripCheckout.booking.views

import android.app.Application
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.ListItemTripDurationBinding
import com.iti.example.findpe2.pojos.TripDuration

class TripDurationsAdapter(var app: Application, var clickListener: TripDurationClickListener) :
    ListAdapter<TripDuration, TripDurationsAdapter.TripDurationViewHolder>(TripDurationItemCallback()) {
    var checkedTripDuration: TripDuration? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TripDurationViewHolder(
            ListItemTripDurationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TripDurationViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, app)
    }

    inner class TripDurationViewHolder(val binding: ListItemTripDurationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //        companion object {
//            fun from(parent: ViewGroup):TripDurationViewHolder{
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = ListItemTripDurationBinding.inflate(layoutInflater,parent,false)
//                return TripDurationViewHolder(binding)
//            }
//        }
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(
            currentItem: TripDuration,
            clickListener: TripDurationClickListener,
            app: Application,
        ) {
            binding.tripDuration = currentItem
            checkedTripDuration?.let {
                if (currentItem == it) {
                    if (binding.layout.cardBackgroundColor.defaultColor != Color.CYAN)
                        binding.layout.setCardBackgroundColor(Color.CYAN)
                    else
                        binding.layout.setCardBackgroundColor(app.getColor(R.color.card_background))
                } else {
                    binding.layout.setCardBackgroundColor(app.getColor(R.color.card_background))
                }
            }
            binding.layout.setOnClickListener {
                clickListener.onClick(currentItem)
                checkedTripDuration = currentItem
                notifyDataSetChanged()
            }
            // A MUST when using data binding with Adapter
            binding.executePendingBindings()
        }
    }

    class TripDurationItemCallback : DiffUtil.ItemCallback<TripDuration>() {
        override fun areItemsTheSame(oldItem: TripDuration, newItem: TripDuration) =
            oldItem.tripId == newItem.tripId

        override fun areContentsTheSame(oldItem: TripDuration, newItem: TripDuration) =
            oldItem == newItem
    }

    class TripDurationClickListener(var clickListener: (TripDuration) -> Unit) {
        fun onClick(tripDuration: TripDuration) = clickListener(tripDuration)
    }
}