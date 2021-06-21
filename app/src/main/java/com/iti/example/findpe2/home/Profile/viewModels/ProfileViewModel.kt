package com.iti.example.findpe2.home.profile.viewModels

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.CompanionUser
import com.iti.example.findpe2.pojos.UserGalleryImage
import com.iti.example.findpe2.pojos.UserInfo
import com.iti.example.findpe2.pojos.UserInfoTitleType
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ProfileViewModel(
    val isShownAsCompanion: Boolean,
    var companionUser: CompanionUser?,
    val isUserAlsoCompanion:Boolean
) : ViewModel() {


    private val auth: FirebaseAuth = Firebase.auth


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

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?>
        get() = _title

    private val _bio = MutableLiveData<String?>()
    val bio: LiveData<String?>
        get() = _bio

    private val _accountLevel = MutableLiveData<String?>()
    val accountLevel: LiveData<String?>
        get() = _accountLevel

    private val _photoPickerStatus = MutableLiveData<Int?>()
    val photoPickerStatus: LiveData<Int?>
        get() = _photoPickerStatus

    private val _onSuccessUploadingImage = MutableLiveData<Boolean?>()
    val onSuccessUploadingImage: LiveData<Boolean?>
        get() = _onSuccessUploadingImage

    

    init {
        _username.value =
            if (isShownAsCompanion) companionUser?.userInfo?.name else auth.currentUser!!.displayName
        _userPhotoUrl.value =
            if (isShownAsCompanion) companionUser?.userInfo?.imageUrl else auth.currentUser!!.photoUrl.toString()
        _title.value = if (isShownAsCompanion) companionUser?.expertLevel else null
        _bio.value = if (isShownAsCompanion) companionUser?.bio else null
        _accountLevel.value = if (isShownAsCompanion) companionUser?.badge else null
        _photoPickerStatus.value = if (isShownAsCompanion) View.GONE else View.VISIBLE

        _userInfoList.value = if (isShownAsCompanion) null else listOf(
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
        
        //get user images from firebase
        viewModelScope.launch {
            try {
                _userImagesUrlList.value = if (isShownAsCompanion) {
                    companionUser?.let {
                        TripApi.getAllImagesForUser(it.companionID).map { url ->
                            UserGalleryImage(url)
                        }
                    }
                } else {
                    auth.currentUser?.let { user ->
                        TripApi.getAllImagesForUser(user.uid).map { url ->
                            UserGalleryImage(url)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.i("ProfileVM", e.localizedMessage)
            }
        }

    }

    fun uploadUserImage(userImage: Bitmap) {
        //compress image
        val baos = ByteArrayOutputStream()
        userImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        //upload image
        val imageRef = FirebaseStorage.getInstance().reference.child("UserProfileImages")
            .child(auth.currentUser?.uid!!).child("profileImages")
            .child(userImage.generationId.toString())
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnCompleteListener {
            if (it.isSuccessful) {
                imageRef.downloadUrl.addOnSuccessListener {
                    auth.currentUser?.let { user ->
                        viewModelScope.launch {
                            try {
                                TripApi.uploadImageForUser(user.uid, it.toString())
                                _userImagesUrlList.value =
                                    TripApi.getAllImagesForUser(user.uid).map { url ->
                                        UserGalleryImage(url)
                                    }
                                _onSuccessUploadingImage.value = true
                            } catch (e: Exception) {
                                Log.i("ProfileVM", e.localizedMessage)
                                _onSuccessUploadingImage.value = false
                            }
                        }
                    }
                }

            }
        }
    }


    fun updateBio(bio:String?){
        //update local companion for the current user object
        if (!bio.isNullOrEmpty()){
            companionUser?.bio = bio
        }
    }


    fun onDoneNotifyingUserOfUploading() {
        _onSuccessUploadingImage.value = null
    }

}