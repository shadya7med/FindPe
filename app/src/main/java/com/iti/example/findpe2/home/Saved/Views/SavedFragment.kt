package com.iti.example.findpe2.home.saved.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentSavedBinding
import com.iti.example.findpe2.home.saved.viewModels.SavedTripsViewModel
import com.iti.example.findpe2.tripCheckout.TripHolderActivity


class SavedFragment : Fragment() {

    private lateinit var savedTripsViewModel: SavedTripsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSavedBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        //start loading
        //
        savedTripsViewModel = ViewModelProvider(this).get(SavedTripsViewModel::class.java)
        binding.savedTripViewModel = savedTripsViewModel

        binding.bookingsListRcyViewSaved.adapter =
            SavedTripsAdapter(SavedTripsAdapter.SavedTripsClickListener {
                savedTripsViewModel.onNavigateToTripDetails(it)
            }, SavedTripsAdapter.SavedTripsClickListener {
                savedTripsViewModel.onIsLikedClicked(it)
            })

        binding.swipeRefreshSavedHome.setOnRefreshListener {
            savedTripsViewModel.getAllSavedTrips()
        }

        savedTripsViewModel.isLiked.observe(viewLifecycleOwner){
            it?.let{
                binding.bookingsListRcyViewSaved.adapter?.notifyDataSetChanged()
            }
        }

        savedTripsViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                //stop loadning
                //show error
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
        savedTripsViewModel.onNavigateToTripDetailsData.observe(viewLifecycleOwner) {
            it?.let { trip ->
                //savedTripsViewModel.getTripSaveState()?.let { isSaved ->
                val openTripDetailsIntent = Intent(activity, TripHolderActivity::class.java)
                openTripDetailsIntent.putExtra(Keys.TRIP_DETAILS_KEY, trip)
                openTripDetailsIntent.putExtra(Keys.IS_SAVED_KEY, true)
                startActivity(openTripDetailsIntent)

                savedTripsViewModel.onDoneNavigationToTripDetails()
                //}
            }
        }


        return binding.root
    }


}