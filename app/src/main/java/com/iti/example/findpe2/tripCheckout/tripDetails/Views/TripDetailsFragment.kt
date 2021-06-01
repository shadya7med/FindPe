package com.iti.example.findpe2.tripCheckout.tripDetails.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentTripDetailsBinding
import com.iti.example.findpe2.pojos.Trip


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
        val trip = arguments?.get("trip") as Trip
        tripId = trip.id

        val viewModelFactory = TripDetailsViewModelFactory((arguments?.get("trip") as Trip))

        viewModel = ViewModelProvider(this, viewModelFactory).get(TripDetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.navigateToBooking.observe(viewLifecycleOwner, Observer { tripId ->
            tripId?.let {
                navController.navigate(TripDetailsFragmentDirections.actionTripDetailsFragmentToBookingFragment(tripId))
                viewModel.displayBookingComplete()
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