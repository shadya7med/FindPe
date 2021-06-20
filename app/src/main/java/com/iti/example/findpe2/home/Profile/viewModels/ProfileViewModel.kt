package com.iti.example.findpe2.home.profile.viewModels

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.iti.example.findpe2.pojos.CompanionUser
import com.iti.example.findpe2.pojos.UserGalleryImage
import com.iti.example.findpe2.pojos.UserInfo
import com.iti.example.findpe2.pojos.UserInfoTitleType
import java.io.ByteArrayOutputStream

class ProfileViewModel(
    isCompanion: Boolean,
    companionUser: CompanionUser?
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

    private var isCompanion: Boolean = false
    private var companion: CompanionUser? = null


    init {
        _username.value =
            if (isCompanion) companionUser?.userInfo?.name else auth.currentUser!!.displayName
        _userPhotoUrl.value =
            if (isCompanion) companionUser?.userInfo?.imageUrl else auth.currentUser!!.photoUrl.toString()
        _title.value = if (isCompanion) companionUser?.expertLevel else null
        _bio.value = if (isCompanion) companionUser?.nationality else null
        _accountLevel.value = if (isCompanion) companionUser?.badge else null
        _photoPickerStatus.value = if (isCompanion) View.GONE else View.VISIBLE
        this.isCompanion = isCompanion
        companionUser?.let {
            companion = it
        }
        //it should be replaced with fetching from API
        _userInfoList.value = if (isCompanion) null else listOf(
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

    fun uploadUserImage(userImage: Bitmap) {
        //scale image
        /*val scaleWidth: Int = userImage.width / 4
        val scaleHeight: Int = userImage.height / 4
        val scaledBitmap =
            Bitmap.createScaledBitmap(userImage, scaleWidth, scaleHeight, true)*/
        //compress image
        val baos = ByteArrayOutputStream()
        userImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        //upload image
        var uploadTask = FirebaseStorage.getInstance().reference.child("UserProfileImages")
            .child(auth.currentUser?.uid!!).child("profileImages")
            .child(userImage.generationId.toString()).putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Log.i("ProfileVM", it.localizedMessage!!)
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            taskSnapshot.metadata?.reference?.downloadUrl?.addOnFailureListener {
                Log.i("ProfileVM", "task Failed")
            }?.addOnSuccessListener {
                //put imageUrl in user Api
                val uploadedImageUrl = it.path
                uploadedImageUrl?.let { url ->
                    Log.i("ProfileVM", url)
                    _onSuccessUploadingImage.value = true
                }
            }

        }
    }

    fun getIsCompanion() = isCompanion
    fun getCompanion() = companion

    fun onDoneNotifyingUserOfUploading() {
        _onSuccessUploadingImage.value = null
    }

}