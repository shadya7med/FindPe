package com.iti.example.findpe2.home.saved.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
            })
        /*savedTripsViewModel.savedTripsList.observe(viewLifecycleOwner){
            it?.let{
                //stop loading
                savedTripsAdapter.submitList(it)
            }

        }*/
        savedTripsViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                //stop loadning
                //show error
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
        savedTripsViewModel.onNavigateToTripDetailsData.observe(viewLifecycleOwner) {
            it?.let {
                val openTripDetailsIntent = Intent(activity, TripHolderActivity::class.java)
                openTripDetailsIntent.putExtra(Keys.TRIP_DETAILS_KEY, it)
                openTripDetailsIntent.putExtra(Keys.IS_SAVED_KEY, true)
                startActivity(openTripDetailsIntent)

                savedTripsViewModel.onDoneNavigationToTripDetails()
            }
        }


        val navController = findNavController()
        //it can also be encapsulated in ViewModel to hide the logic happens with the navigation
        binding.filterBtnSavedHome.setOnClickListener {
            navController.navigate(SavedFragmentDirections.actionSavedFragmentHomeToFilterFragment())
        }

        // saved State handle is  a map for returning date between fragments
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String, Any>>(
            Keys.FULL_FILTER_MAP_KEY
        )?.observe(
            viewLifecycleOwner
        ) { result ->
            // Do something with the result.
            //savedTripsViewModel.getFilteredTrips(result)
            Log.i("FiSav", "${result[Keys.MIN_RANGE_KEY] as Double}")
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //to refresh the UI when coming back from Trip details
        savedTripsViewModel.getAllSavedTrips()
    }
}