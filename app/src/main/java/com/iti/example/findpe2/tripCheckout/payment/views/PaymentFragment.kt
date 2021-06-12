package com.iti.example.findpe2.tripCheckout.payment.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.iti.example.findpe2.databinding.FragmentPaymentBinding
import com.iti.example.findpe2.tripCheckout.payment.viewmodels.PaymentViewModel
import com.iti.example.findpe2.tripCheckout.payment.viewmodels.PaymentViewModelFactory

class PaymentFragment : Fragment() {


    private lateinit var binding:FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater,parent,false)
        val args by navArgs<PaymentFragmentArgs>()
        val viewModelFactory = PaymentViewModelFactory(args.tripPrice, args.numOfSeats)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(PaymentViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        return binding.root
    }
}