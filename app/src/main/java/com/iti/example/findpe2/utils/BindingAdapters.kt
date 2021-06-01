package com.iti.example.findpe2.utils

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iti.example.findpe2.R
import com.iti.example.findpe2.home.chat.chatRoomsList.views.ChatFragment
import com.iti.example.findpe2.home.travelling.views.TravellingTripAdapter
import com.iti.example.findpe2.pojos.ChatRoom
import com.iti.example.findpe2.pojos.Trip
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("chatRoomImage")
fun ImageView.setChatRoomImage(chatRoom: ChatRoom) {
    chatRoom.destinationUserImage?.let { userImageUrl ->
        if (userImageUrl.isEmpty()) {
            setImageResource(R.drawable.ic_account_circle_black_36dp)
        } else {
            Glide
                .with(context)
                .load(userImageUrl)
                .centerCrop()
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.loading_animation)
                .into(this)
        }
    }
}

@BindingAdapter("lastMsgTime")
fun TextView.setLastMsgTime(chatRoom: ChatRoom) {
    chatRoom.chatLastMsgTime?.let { lastMsgDate ->
        val cal = Calendar.getInstance()
        val currentDate = cal.time
        //set Calender to yesterday
        cal.timeInMillis = currentDate.time - ChatFragment.ONE_DAY_MILLIS
        if (lastMsgDate.after(cal.time)) {
            val hourDateFormat = SimpleDateFormat("HH:mm", Locale.US)
            //show msg hour
            val lastMsgHour = hourDateFormat.format(lastMsgDate)
            text = lastMsgHour
        } else {
            //set calender to 4 days ago
            cal.timeInMillis = currentDate.time - ChatFragment._4_DAYS_MILLIS
            if (lastMsgDate.after(cal.time)) {
                val dayDateFormat = SimpleDateFormat("EEE", Locale.US)
                val lastMsgDay = dayDateFormat.format(lastMsgDate)
                //show msg day
                text = lastMsgDay
            } else {
                //set calender to one year ago
                cal.timeInMillis = currentDate.time - ChatFragment.ONE_YEAR_MILLIS
                if (lastMsgDate.after(cal.time)) {
                    //show msg month,day
                    val monthDayFormat = SimpleDateFormat("MMM dd", Locale.US)
                    val lastMsgMonthDay = monthDayFormat.format(lastMsgDate)
                    text = lastMsgMonthDay
                } else {
                    //show msg month,day,year
                    val monthDayYearFormat = SimpleDateFormat("MMM dd yyyy", Locale.US)
                    val lastMsgMonthDayYear = monthDayYearFormat.format(lastMsgDate)
                    text = lastMsgMonthDayYear
                }


            }
        }

    }
}
@BindingAdapter("listTrips")
fun RecyclerView.bind(list: List<Trip>?){
    (this.adapter as TravellingTripAdapter).submitList(list)
}
@BindingAdapter("imageUrl")
fun ImageView.bind(url: String?){
    val imageUri = Uri.parse(url).buildUpon().scheme("https").build()
    Glide.with(this.context)
        .load(imageUri)
        .apply(
            RequestOptions()
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image))
        .into(this)

}