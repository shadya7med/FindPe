package com.iti.example.findpe2.companionHolder.registration.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.example.findpe2.pojos.CompanionUser
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.iti.example.findpe2.models.CountryCityApiModel
import com.iti.example.findpe2.pojos.CountriesApiResponse
import kotlinx.coroutines.launch

private const val TAG = "RegistrationViewModel"

class RegistrationViewModel : ViewModel() {

    val companion = MutableLiveData<CompanionUser>()

    private val _loadingVisibility = MutableLiveData<Int?>()
    val loadingVisibility: LiveData<Int?>
        get() = _loadingVisibility


    private val _selectedCountry = MutableLiveData<String?>()
    val selectedCountry: LiveData<String?>
        get() = _selectedCountry

    private val _navigateToLevelSelector = MutableLiveData<Boolean?>()
    val navigateToLevelSelector: LiveData<Boolean?>
        get() = _navigateToLevelSelector

    private val response = MutableLiveData<CountriesApiResponse?>()

    val countryList = Transformations.map(response) {
        it?.data?.map { countryData ->
            countryData.country
        } ?: listOf<String>("Country")
    }

    val cityList = Transformations.map(selectedCountry) {
        if (it != null) {
            val filteredCountry = response.value?.data?.filter { countryData ->
                countryData.country == it
            }
            filteredCountry?.get(0)?.cities
        } else {
            listOf<String>("City")
        }
    }

    fun displayLevelSelector() {
        _navigateToLevelSelector.value = true
    }

    fun displayLevelSelectorComplete() {
        _navigateToLevelSelector.value = null
    }

    init {
        response.value = null
        _selectedCountry.value = null
        _loadingVisibility.value = View.GONE
        getCountries()
    }

    private fun getCountries() {
        _loadingVisibility.value = View.VISIBLE
        viewModelScope.launch {
            try {
                response.value = CountryCityApiModel.getAllCountries()
                _loadingVisibility.value = View.GONE

                Log.i(TAG, "getCountries: ${response.value}")

            } catch (t: Throwable) {
                Log.i(TAG, "getCountries: ${t.localizedMessage}")

            }
        }
    }

    fun setSelectedCountry(country: String) {
        _selectedCountry.value = country
    }
}