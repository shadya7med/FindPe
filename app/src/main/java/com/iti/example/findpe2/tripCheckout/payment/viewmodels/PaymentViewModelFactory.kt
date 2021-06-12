package com.iti.example.findpe2.tripCheckout.payment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.tripCheckout.booking.viewmodels.BookingViewModel

class PaymentViewModelFactory (
    private val tripPrice: Int,
    private val noOfSeats: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(tripPrice, noOfSeats) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}