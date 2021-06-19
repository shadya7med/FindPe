package com.iti.example.findpe2.home.allTrips.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        allTripsViewModel.setAllTrips(
            AllTripsFragmentArgs.fromBundle(requireArguments()).allTrips.asList(),
            AllTripsFragmentArgs.fromBundle(requireArguments()).allSavedTripsIDs.asList()
        )

        binding.tripsListRcyViewAllTripsHome.adapter = AllTripsAdapter(OnClickListener {
            allTripsViewModel.onNavigateToTripDetails(it)
        })
        allTripsViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        allTripsViewModel.selectedTrip.observe(viewLifecycleOwner) {
            it?.let { trip ->
                allTripsViewModel.getTripSaveState()?.let { isSaved ->
                    val openTripDetailsIntent = Intent(activity, TripHolderActivity::class.java)
                    openTripDetailsIntent.putExtra(Keys.TRIP_DETAILS_KEY, trip)
                    openTripDetailsIntent.putExtra(Keys.IS_SAVED_KEY, isSaved)
                    startActivity(openTripDetailsIntent)

                    allTripsViewModel.onDoneNavigationToTripDetails()
                }
            }
        }
        val navController = findNavController()
        allTripsViewModel.onNavigateToFilter.observe(viewLifecycleOwner){
            it?.let{
                navController.navigate(AllTripsFragmentDirections.actionAllTripsFragmentHomeToFilterFragment())
                allTripsViewModel.onDoneNavigationToFilter()
            }
        }
        // saved State handle is  a map for returning date between fragments
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String, Any>>(
            Keys.FULL_FILTER_MAP_KEY
        )?.observe(
            viewLifecycleOwner, Observer { result ->
                allTripsViewModel.filterTrips(result)
                navController.currentBackStackEntry?.savedStateHandle?.remove<MutableMap<String,Any>>(Keys.FULL_FILTER_MAP_KEY)
            })
        binding.swipeRefreshAllTripsHome.setOnRefreshListener {

            allTripsViewModel.getAllTrips()
        }


        return binding.root
    }


}