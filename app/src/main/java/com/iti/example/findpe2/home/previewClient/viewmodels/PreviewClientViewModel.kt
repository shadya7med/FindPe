package com.iti.example.findpe2.home.previewClient.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.UserGalleryImage
import kotlinx.coroutines.launch

class PreviewClientViewModel(clientID:String):ViewModel() {

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String>
        get() = _username

    private val _userPhotoUrl: MutableLiveData<String> = MutableLiveData()
    val userPhotoUrl: LiveData<String>
        get() = _userPhotoUrl

    private val _userImagesUrlList = MutableLiveData<List<UserGalleryImage>?>()
    val userImageUrlList: LiveData<List<UserGalleryImage>?>
        get() = _userImagesUrlList

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus : LiveData<Int?>
        get() = _loadingStatus

    init {
        _loadingStatus.value = View.GONE
        getPreviewedUserByID(clientID)
    }

    private fun getPreviewedUserByID(clientID:String){
        _loadingStatus.value = View.VISIBLE
        viewModelScope.launch {
            try{
                val user = TripApi.getUserByID(clientID)
                _username.value = user.name
                _userPhotoUrl.value = user.imageUrl
                _userImagesUrlList.value = TripApi.getAllImagesForUser(clientID).map{
                    UserGalleryImage(it)
                }
            }catch (e:Exception){
                Log.i("Preview", e.localizedMessage)
            }finally {
                _loadingStatus.value = View.GONE
            }
        }
    }

}