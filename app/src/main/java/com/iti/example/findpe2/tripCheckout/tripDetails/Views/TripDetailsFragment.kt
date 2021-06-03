package com.iti.example.findpe2.tripCheckout.tripDetails.views

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentTripDetailsBinding
import com.iti.example.findpe2.pojos.Trip
import com.iti.example.findpe2.tripCheckout.tripDetails.viewModels.TripDetailsViewModel
import com.iti.example.findpe2.tripCheckout.tripDetails.viewModels.TripDetailsViewModelFactory


class TripDetailsFragment:Fragment() {


    lateinit var binding:FragmentTripDetailsBinding
    lateinit var viewModel: TripDetailsViewModel
    private lateinit var navController: NavController

    //it should be obtained from the Intent that open this trip details
    private var tripId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTripDetailsBinding.inflate(inflater,parent,false)
        val trip = arguments?.get(Keys.TRIP_DETAILS_KEY) as Trip
        val isSaved = arguments?.get(Keys.IS_SAVED_KEY) as Boolean
        Log.i("tripDetails", "$isSaved")
        tripId = trip.id

        val viewModelFactory = TripDetailsViewModelFactory(trip,isSaved)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TripDetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner // better that this in case of fragments

        viewModel.navigateToBooking.observe(viewLifecycleOwner, { tripId ->
            tripId?.let {
                navController.navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToBookingFragment(tripId))
                viewModel.displayBookingComplete()
            }
        })
        viewModel.navigateToTimeline.observe(viewLifecycleOwner, Observer { tripId ->
            tripId?.let {
                navController.navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToTimelineFragment(tripId))
                viewModel.displayTimelineComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.trip_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                activity?.finish()
                true
            }
            R.id.book_trip -> {
                viewModel.displayBooking(tripId)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}