package com.iti.example.findpe2.tripCheckout.payment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PaymentViewModel(private val tripPrice: Float, private val tripNoOfSeats: Int): ViewModel() {
    private val _price = MutableLiveData<Float?>()
    val price: LiveData<Float?>
        get() = _price

    private val _noOfSeats = MutableLiveData<Int?>()
    val noOfSeats: LiveData<Int?>
        get() = _noOfSeats

    val totalPrice = Transformations.map(price){
        it?.times(noOfSeats.value ?: 0)
    }

    init {
        _noOfSeats.value = tripNoOfSeats
        _price.value = tripPrice
    }

}