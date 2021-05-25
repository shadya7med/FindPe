package com.iti.example.findpe2.tripCheckout.payment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iti.example.findpe2.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {


    private lateinit var binding:FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater,parent,false)
        return binding.root
    }
}