package com.iti.example.findpe2.home.allTrips.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentAllTripsBinding
import com.iti.example.findpe2.home.allTrips.viewModels.AllTripsViewModel
import com.iti.example.findpe2.home.travelling.views.OnClickListener
import com.iti.example.findpe2.tripCheckout.TripHolderActivity


class AllTripsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAllTripsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val allTripsViewModel = ViewModelProvider(this).get(AllTripsViewModel::class.java)
        binding.allTripViewModel = allTripsViewModel

        allTripsViewModel.setAllTrips(AllTripsFragmentArgs.fromBundle(requireArguments()).allTrips.asList())

        binding.tripsListRcyViewAllTripsHome.adapter = AllTripsAdapter(OnClickListener {
            allTripsViewModel.onNavigateToTripDetails(it)
        })
        allTripsViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()
            }
        }
        allTripsViewModel.selectedTrip.observe(viewLifecycleOwner) {
            it?.let { trip ->
                val openTripDetailsIntent = Intent(activity, TripHolderActivity::class.java)
                openTripDetailsIntent.putExtra(Keys.TRIP_DETAILS_KEY, trip)
                openTripDetailsIntent.putExtra(Keys.IS_SAVED_KEY,false)
                startActivity(openTripDetailsIntent)

                allTripsViewModel.onDoneNavigationToTripDetails()
            }
        }



        return binding.root
    }


}