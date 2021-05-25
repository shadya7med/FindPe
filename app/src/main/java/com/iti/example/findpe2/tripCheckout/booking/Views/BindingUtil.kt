package com.iti.example.findpe2.tripCheckout.booking.views

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.iti.example.findpe2.pojos.TripInfo

@BindingAdapter("tripId")
fun TextView.setTripId(tripInfo:TripInfo){
    text = tripInfo.tripId.toString()
}