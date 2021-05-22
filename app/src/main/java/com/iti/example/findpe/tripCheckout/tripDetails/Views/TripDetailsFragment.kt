package com.iti.example.findpe.tripCheckout.tripDetails.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe.databinding.FragmentTripDetailsBinding

class TripDetailsFragment:Fragment() {


    lateinit var binding:FragmentTripDetailsBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripDetailsBinding.inflate(inflater,parent,false)
        binding.tripDetailsFragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    fun navigateToTripBooking(){
        navController.navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToBookingFragment())
    }
}