package com.iti.example.findpe2.home.profile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.pojos.UserGalleryImage
import com.iti.example.findpe2.pojos.UserInfo
import com.iti.example.findpe2.pojos.UserInfoTitleType

class ProfileViewModel : ViewModel() {


    var auth: FirebaseAuth = Firebase.auth

    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: LiveData<String>
        get() = _email

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String>
        get() = _username

    private val _userPhotoUrl: MutableLiveData<String> = MutableLiveData()
    val userPhotoUrl: LiveData<String>
        get() = _userPhotoUrl

    private val _userInfoList = MutableLiveData<List<UserInfo>?>()
    val userInfoList: LiveData<List<UserInfo>?>
        get() = _userInfoList

    private val _userImagesUrlList = MutableLiveData<List<UserGalleryImage>?>()
    val userImageUrlList: LiveData<List<UserGalleryImage>?>
        get() = _userImagesUrlList


    init {
        _email.value = auth.currentUser!!.email
        _username.value = auth.currentUser!!.displayName
        _userPhotoUrl.value = auth.currentUser!!.photoUrl.toString()
        //it should be replaced with fetching from API
        _userInfoList.value = listOf(
            UserInfo(
                UserInfoTitleType.TRIPS_HISTORY,
                arrayListOf("Dahab", "Cairo", "Jordan", "Alexandria")
            ),
            UserInfo(
                UserInfoTitleType.WORK_HISTORY,
                arrayListOf("Khaled Hassan", "Shady Ahmed", "Saeed El-Soudany")
            ),
            UserInfo(
                UserInfoTitleType.PERKS,
                arrayListOf("Car", "Motorcycle")
            ),
            UserInfo(
                UserInfoTitleType.Languages,
                arrayListOf("Arabic", "English", "French")
            )
        )
        _userImagesUrlList.value = listOf(
            UserGalleryImage("https://picsum.photos/id/237/200"),
            UserGalleryImage("https://picsum.photos/id/10/100"),
            UserGalleryImage("https://picsum.photos/id/100/500"),
            UserGalleryImage("https://picsum.photos/id/101/320"),
            UserGalleryImage("https://picsum.photos/id/1015/250"),
            UserGalleryImage("https://picsum.photos/id/1016/350"),
            UserGalleryImage("https://picsum.photos/id/237/200"),
        )
    }

}