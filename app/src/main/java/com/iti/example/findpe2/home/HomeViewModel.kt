package com.iti.example.findpe2.home

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.constants.Constants
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.CompanionUser
import com.iti.example.findpe2.pojos.User
import com.iti.example.findpe2.pojos.UserLocation
import kotlinx.coroutines.launch


class HomeViewModel(
    private val locationProviderClient: FusedLocationProviderClient,
    application: Application
) : AndroidViewModel(application) {


    var auth: FirebaseAuth = Firebase.auth

    private val _companionList = MutableLiveData<List<CompanionUser>?>()

    val companionSecVisibility = Transformations.map(_companionList) {
        if (it != null) {
            val companionIdList = it.map { companion ->
                companion.companionID
            }
            companionIdList.contains(FirebaseAuth.getInstance().currentUser!!.uid)
        } else {
            false
        }
    }

    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String>
        get() = _email

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String>
        get() = _username

    private val _userPhotoUrl: MutableLiveData<String> = MutableLiveData()
    val userPhotoUrl: LiveData<String>
        get() = _userPhotoUrl

    private val _onNavigateToProfile = MutableLiveData<Boolean?>()
    val onNavigateToProfile: LiveData<Boolean?>
        get() = _onNavigateToProfile

    private var isUserAlsoCompanion = false
    private var userAlsoCompanion: CompanionUser? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            auth.currentUser?.let {
                FirebaseDatabase.getInstance().reference
                    .child("UsersLocation")
                    .child(it.uid)
                    .setValue(
                        UserLocation(
                            locationResult.locations[0].latitude,
                            locationResult.locations[0].longitude
                        )
                    )
            }
        }
    }

    init {
        _email.value = auth.currentUser!!.email
        _username.value = auth.currentUser!!.displayName
        _userPhotoUrl.value = auth.currentUser!!.photoUrl.toString()
        viewModelScope.launch {
            isUserAlsoCompanion = try {
                userAlsoCompanion = TripApi.getACompanionById(auth.currentUser!!.uid)
                true
            } catch (e: Exception) {
                Log.i("HomeVM", e.localizedMessage)
                false
            }


        }
        _companionList.value = null
        saveCurrentUser()
        getCompanionList()
    }

    fun getIsUserAlsoCompanion() = isUserAlsoCompanion
    fun getUserAlsoCompanion() = userAlsoCompanion


    private fun saveCurrentUser() {
        viewModelScope.launch {
            val currentUser = Firebase.auth.currentUser
            currentUser?.let { currentUser ->
                try {
                    TripApi.getUserByID(currentUser.uid)//TripApi.getAllUsers() //it should be replaced with getting a single user
                    //current user already added do nothing
                    /*val usersIDs = users.map {
                        it.userID
                    }*/
                    /*if (usersIDs.contains(currentUser.uid)) {
                        //Do Nothing it's already registered
                    } else {*/

                    //}
                } catch (e: Exception) {
                    Log.i("TravelingVM", e.localizedMessage)
                    //user not added Yet
                    val email = currentUser.email ?: currentUser.providerData[1].email
                    email?.let { email ->
                        try {
                            TripApi.addANewUser(
                                User(
                                    currentUser.uid,
                                    currentUser.displayName ?: Constants.USER_DEFAULT_NAME,
                                    Constants.USER_DEFAULT_PASSWORD,
                                    email,
                                    currentUser.photoUrl.toString(),
                                    false,
                                    0,
                                    currentUser.photoUrl.toString(),
                                    "user"
                                )
                            )
                        } catch (e: Exception) {
                            Log.i("TravelingVM", "user is not posted ${e.localizedMessage}")
                        }
                    }
                }
            }

        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 30000 // 30 Secs
            fastestInterval = 15000// 15 Secs
            smallestDisplacement = 100f // 100 meter
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    fun stopLocationUpdates() {
        locationProviderClient.removeLocationUpdates(locationCallback)
    }


    private fun getCompanionList() {
        viewModelScope.launch {
            try {
                _companionList.value = TripApi.getAllCompanions()
            } catch (t: Throwable) {

            }
        }
    }

    fun onNavigateToProfile() {
        _onNavigateToProfile.value = true
    }

    fun onDoneNavigationToProfile() {
        _onNavigateToProfile.value = null
    }

    fun logout() {
        auth.signOut()
    }

}