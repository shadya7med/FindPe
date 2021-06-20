package com.iti.example.findpe2.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iti.example.findpe2.R
import com.iti.example.findpe2.home.allTrips.views.AllTripsAdapter
import com.iti.example.findpe2.home.chat.chatInstance.views.MessagesListAdapter
import com.iti.example.findpe2.home.chat.chatRoomsList.views.ChatFragment
import com.iti.example.findpe2.home.chat.chatRoomsList.views.ChatRoomsListAdapter
import com.iti.example.findpe2.home.companionsList.views.CompanionListAdapter
import com.iti.example.findpe2.home.discover.views.DiscoverFeaturedAdapter
import com.iti.example.findpe2.home.discover.views.DiscoverPlacesAdapter
import com.iti.example.findpe2.home.profile.views.UserGalleryAdapter
import com.iti.example.findpe2.home.profile.views.UserInfoAdapter
import com.iti.example.findpe2.home.saved.views.SavedTripsAdapter
import com.iti.example.findpe2.home.timeline.views.TimelineAdapter
import com.iti.example.findpe2.home.travelling.views.TravellingTripAdapter
import com.iti.example.findpe2.pojos.*
import com.iti.example.findpe2.tripCheckout.booking.views.TripDurationsAdapter
import com.iti.example.findpe2.tripCheckout.tripDetails.viewModels.SaveState
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "bindAdapters"

@BindingAdapter("chatRoomImage")
fun ImageView.setChatRoomImage(chatRoom: ChatRoom) {
    chatRoom.destinationUserImage?.let { userImageUrl ->
        if (userImageUrl.isEmpty()) {
            setImageResource(R.drawable.ic_account_circle_black_36dp)
        } else {
            Glide
                .with(context)
                .load(userImageUrl)
                .circleCrop()
                .error(R.drawable.ic_broken_image)
                .placeholder(R.drawable.loading_animation)
                .into(this)
        }
    }
}

/*@BindingAdapter("tripImage")
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
}*/

@BindingAdapter("userImage")
fun ImageView.setUSerImage(userImageUrl: String?) {
    if (userImageUrl.isNullOrEmpty()) {
        setImageResource(R.drawable.ic_account_circle_black_36dp)
    } else {
        Glide
            .with(context)
            .load(userImageUrl)
            .circleCrop()
            .error(R.drawable.ic_account_circle_black_36dp)
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
fun RecyclerView.setListTrips(list: List<Trip>?) {
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
fun RecyclerView.setListFeaturedTrips(list: List<Trip>?) {
    (this.adapter as DiscoverFeaturedAdapter).submitList(list)
}

@BindingAdapter("listUserInfo")
fun RecyclerView.setListUserInfo(list: List<UserInfo>?) {
    visibility = if (list.isNullOrEmpty()) View.GONE else View.VISIBLE
    (this.adapter as UserInfoAdapter).submitList(list)
}

@BindingAdapter("listUserGallery")
fun RecyclerView.setListUserGallery(list: List<UserGalleryImage>?) {
    (this.adapter as UserGalleryAdapter).submitList(list)
}

@BindingAdapter("listCompanions")
fun RecyclerView.setListCompanion(list: List<CompanionUser>?) {
    (this.adapter as CompanionListAdapter).submitList(list)
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

@BindingAdapter("listTimeline")
fun RecyclerView.setListTimeline(list: List<TimelineSlot>?) {
    (this.adapter as TimelineAdapter).submitList(list)
}

@BindingAdapter("imageUrl")
fun ImageView.bindImage(tripImages: List<TripImage>?) {
    tripImages?.let {
        if (it.isNotEmpty()) {
            val image = it[0].image.replace("\\", "//", false)
            val imageUri =
                "http://rsaber-001-site1.ftempurl.com/$image"
            Glide.with(this.context)
                .load(imageUri)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(this)
        }
        return
    }
    setImageResource(R.drawable.dahab)

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

@BindingAdapter("userInfoImage")
fun ImageView.setUserInfoImage(userInfo: UserInfo) {
    setImageResource(
        when (userInfo.title) {
            UserInfoTitleType.WORK_HISTORY -> R.drawable.ic_outline_local_activity_24
            UserInfoTitleType.TRIPS_HISTORY -> R.drawable.ic_baseline_sticky_note_2_24
            UserInfoTitleType.PERKS -> R.drawable.ic_baseline_person_search_24
            UserInfoTitleType.Languages -> R.drawable.ic_baseline_translate_24
        }
    )
}


@BindingAdapter("userGalleryImage")
fun ImageView.setUserGalleryImage(userGalleryImage: UserGalleryImage) {
    if (!userGalleryImage.imageUrl.isNullOrEmpty()) {
        Glide
            .with(context)
            .load(userGalleryImage.imageUrl)
            .error(R.drawable.ic_broken_image)
            .placeholder(R.drawable.loading_animation)
            .into(this)
    }
}

@BindingAdapter("userInfoListToString")
fun TextView.setUserInfoListToString(userInfoSubTitles: List<String>) {
    var listToString = ""
    for (item in userInfoSubTitles) {
        listToString += item
        listToString += " ,"
    }
    listToString = listToString.substring(0, listToString.length - 2)
    text = listToString
}

@BindingAdapter("isLiked")
fun ImageView.setIsLiked(companionUser: CompanionUser) {
    setImageResource(
        when (companionUser.isLiked) {
            true -> R.drawable.filled_heart
            false -> R.drawable.heart_black_outlined
        }
    )
}

@BindingAdapter("bindTripDurationList")
fun RecyclerView.bindList(list: List<TripDuration>?) {
    (this.adapter as TripDurationsAdapter).submitList(list)

}

@BindingAdapter("feature", "featuresList")
fun Button.setFeatureFromFeatureList(feature: Int?, featuresList: List<Boolean>?) {
    this as MaterialButton
    featuresList?.let {
        feature?.let { feature ->
            if (it[feature]) {
                //true --> active feature
                setIconTintResource(R.color.feature_selected_btn_icon)
                backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.feature_selected_btn_background))
            } else {
                //false --> inactive feature
                setIconTintResource(R.color.feature_unselected_btn_icon)
                backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.feature_unselected_btn_background))
            }
        }
    }
}

@BindingAdapter("refreshingIndicatorVisibility")
fun SwipeRefreshLayout.setRefreshIndicatorVisibility(loadingState: Int?) {
    loadingState?.let {
        isRefreshing = when (it) {
            View.VISIBLE -> true
            else -> false
        }
    }

}

@BindingAdapter("tripLikeIcon")
fun ImageView.setTripLikeIcon(isLiked: Boolean?) {
    isLiked?.let {
        setImageResource(
            when (it) {
                true -> R.drawable.filled_heart
                false -> R.drawable.heart
            }
        )
    }
}

@BindingAdapter("listPlaces")
fun RecyclerView.bindPlaceList(list: List<PlaceToVisit>?) {
    (this.adapter as DiscoverPlacesAdapter).submitList(list)
}

@BindingAdapter("bindPlaceImageUrl")
fun ImageView.bindPlace(placeImageUrl: String?) {
    if (placeImageUrl != null) {
        Glide.with(this.context)
            .load(placeImageUrl)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(this)
    } else {
        this.setImageResource(R.drawable.no_image_preview)
    }
}

@BindingAdapter("companionExpertLevel")
fun TextView.setCompanionExpertLevel(expertLevel: String?) {
    expertLevel?.let {
        text = when (it) {
            ExpertLevel.PROFESSIONAL.value -> "Professional Companion"
            ExpertLevel.Amateur.value -> "Amateur Companion"
            else -> "Unregistered level"
        }
    }
}

@BindingAdapter("accountLevel")
fun ImageView.setAccountLevel(badge: String?) {
    badge?.let {
        visibility = View.VISIBLE
        setImageResource(
            when (it) {
                AccountLevel.GOLD.value -> R.drawable.ic_baseline_gold_badge
                AccountLevel.SILVER.value -> R.drawable.ic_baseline_silver_badge
                AccountLevel.BRONZE.value -> R.drawable.ic_baseline_bronze_badge
                else -> R.drawable.ic_baseline_local_police_24
            }
        )
        return
    }
    visibility = View.GONE
}

@BindingAdapter("profileSubtitle")
fun TextView.setProfileSubtitle(subtitle: String?) {
    text = if (subtitle.isNullOrEmpty()) {
        visibility = View.GONE
        ""
    } else {
        visibility = View.VISIBLE
        subtitle
    }
}

@BindingAdapter("listCountries")
fun AutoCompleteTextView.listCountries(countries: List<String>?) {
    countries?.let {
        this.setAdapter(ArrayAdapter(this.context, android.R.layout.simple_list_item_1, it))
    }
}

@BindingAdapter("listCountrieCities")
fun Spinner.listCountries(countries: List<String>?) {
    countries?.let {
        adapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, it)
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        this.adapter = adapter
    }
}
