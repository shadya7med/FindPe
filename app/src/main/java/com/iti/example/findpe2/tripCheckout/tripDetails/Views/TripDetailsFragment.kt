package com.iti.example.findpe2.tripCheckout.tripDetails.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe2.databinding.FragmentTripDetailsBinding
import com.iti.example.findpe2.pojos.Trip


class TripDetailsFragment:Fragment() {


    lateinit var binding:FragmentTripDetailsBinding
    private lateinit var navController: NavController

    //it should be obtained from the Intent that open this trip details
    private var tripId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripDetailsBinding.inflate(inflater,parent,false)
        val trip = arguments?.get("trip") as Trip
        tripId = trip.id

        val viewModelFactory = TripDetailsViewModelFactory((arguments?.get("trip") as Trip))

        val viewModel = ViewModelProvider(this, viewModelFactory).get(TripDetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    fun navigateToTripBooking(){
        navController.navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToBookingFragment(tripId))
    }
}