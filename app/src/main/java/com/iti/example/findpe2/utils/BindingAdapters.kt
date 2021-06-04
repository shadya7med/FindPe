package com.iti.example.findpe2.utils

import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iti.example.findpe2.R
import com.iti.example.findpe2.home.allTrips.views.AllTripsAdapter
import com.iti.example.findpe2.home.chat.chatInstance.views.MessagesListAdapter
import com.iti.example.findpe2.home.chat.chatRoomsList.views.ChatFragment
import com.iti.example.findpe2.home.chat.chatRoomsList.views.ChatRoomsListAdapter
import com.iti.example.findpe2.home.discover.views.DiscoverFeaturedAdapter
import com.iti.example.findpe2.home.saved.views.SavedTripsAdapter
import com.iti.example.findpe2.home.travelling.views.TravellingTripAdapter
import com.iti.example.findpe2.pojos.ChatRoom
import com.iti.example.findpe2.pojos.Message
import com.iti.example.findpe2.pojos.Trip
import com.iti.example.findpe2.pojos.TripInfo
import com.iti.example.findpe2.tripCheckout.tripDetails.viewModels.SaveState
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

@BindingAdapter("tripImage")
fun ImageView.setTripImage(trip: Trip) {
    if (trip.tripImages.isNullOrEmpty()) {
        setImageResource(R.drawable.dahab)
    } else {
        Glide
            .with(context)
            .load(trip.tripImages[0])
            .centerCrop()
            .error(R.drawable.ic_broken_image)
            .placeholder(R.drawable.loading_animation)
            .into(this)
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
fun RecyclerView.bind(list: List<Trip>?) {
    (this.adapter as TravellingTripAdapter).submitList(list)
}

@BindingAdapter("listChatRooms")
fun RecyclerView.setChatRoomsList(list: List<ChatRoom>?) =
    (this.adapter as ChatRoomsListAdapter).submitList(list)

@BindingAdapter("listChatMessages")
fun RecyclerView.setChatMessages(list: List<Message>?) =
    (this.adapter as MessagesListAdapter).submitList(list)

@BindingAdapter("listSavedTrips")
fun RecyclerView.setListSavedTrips(list: List<Trip>?) {
    (this.adapter as SavedTripsAdapter).submitList(list)
}
@BindingAdapter("listAllTrips")
fun RecyclerView.setListAllTrips(list: List<Trip>?) {
    (this.adapter as AllTripsAdapter).submitList(list)
}
@BindingAdapter("listFeaturedTrips")
fun RecyclerView.setListFeaturedTrips(list:List<Trip>?){
    (this.adapter as DiscoverFeaturedAdapter).submitList(list)
}

@BindingAdapter("visibilityAgainstStatus")
fun View.setVisibilityAgainstStatus(status: LiveData<Int?>) {
    status.value?.let { loading ->
        this.visibility = when (loading) {
            View.VISIBLE -> View.GONE
            else -> View.VISIBLE
        }

    }
}


@BindingAdapter("imageUrl")
fun ImageView.bind(url: String?) {
    val imageUri = Uri.parse(url).buildUpon().scheme("https").build()
    Glide.with(this.context)
        .load(imageUri)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(this)

}

@BindingAdapter("tripId")
fun TextView.setTripId(tripInfo: TripInfo) {
    text = tripInfo.tripId.toString()
}

@BindingAdapter("saveButtonIcon")
fun FloatingActionButton.setSavedButtonIcon(saveState: SaveState) {
    when (saveState) {
        SaveState.SAVED -> {
            setImageResource(R.drawable.ic_round_check_24)
            setBackgroundColor(Color.WHITE)
        }
        SaveState.LOADING -> {
            setImageResource(R.drawable.saving_animation)
            setBackgroundColor(Color.BLACK)
        }
        SaveState.NOT_SAVED -> {
            setImageResource(R.drawable.ic_baseline_save_24)
            setBackgroundColor(Color.WHITE)
        }
    }
}