package com.iti.example.findpe2.home.profile.bio.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.example.findpe2.models.TripApi
import com.iti.example.findpe2.pojos.CompanionUser
import kotlinx.coroutines.launch

class EditBioViewModel(private val currentCompanion: CompanionUser?) : ViewModel() {

    private val _bio = MutableLiveData<String?>()
    val bio: LiveData<String?>
        get() = _bio

    private val _onUpdateBio = MutableLiveData<Boolean?>()
    val onUpdateBio: LiveData<Boolean?>
        get() = _onUpdateBio

    private val _loadingStatus = MutableLiveData<Int?>()
    val loadingStatus: LiveData<Int?>
        get() = _loadingStatus

    private var updatedBio = ""

    init {
        _loadingStatus.value = View.GONE
        currentCompanion?.let{
            _bio.value = it.bio
        }
    }

    fun updateBioForCompanion(bio: String) {
        currentCompanion?.let { currentCompanion ->
            _loadingStatus.value = View.VISIBLE
            currentCompanion.bio = bio
            viewModelScope.launch {
                try {
                    TripApi.updateACompanion(currentCompanion)
                    updatedBio = bio
                    _onUpdateBio.value = true
                } catch (e: Exception) {
                    Log.i("EditBioVM", e.localizedMessage)
                    updatedBio = ""
                    _onUpdateBio.value = false
                } finally {
                    _loadingStatus.value = View.GONE
                }
            }
        }
    }

    fun getUpdatedBio() = updatedBio

    fun onDoneUpdatingBio() {
        _onUpdateBio.value = null
    }


}