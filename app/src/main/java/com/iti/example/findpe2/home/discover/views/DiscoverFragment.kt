package com.iti.example.findpe2.home.discover.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentDiscoverBinding
import com.iti.example.findpe2.home.discover.viewsModels.DiscoverViewModel
import com.iti.example.findpe2.home.explore.views.ExploreFragmentDirections
import com.iti.example.findpe2.home.travelling.views.OnClickListener
import com.iti.example.findpe2.tripCheckout.TripHolderActivity


class DiscoverFragment : Fragment() {


    private lateinit var discoverViewModel: DiscoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDiscoverBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        binding.discoverViewModel = discoverViewModel
        val navController = findNavController()
        discoverViewModel.onNavigateToSeeAllClicked.observe(viewLifecycleOwner) {
            it?.let {
                discoverViewModel.getSavedTripsLis()?.let { savedTripsList ->
                    navController.navigate(
                        ExploreFragmentDirections.actionExploreFragmentHomeToAllTripsFragment(
                            it.toTypedArray(),
                            savedTripsList.toIntArray()
                        )
                    )
                    discoverViewModel.onDoneNavigationToSeeAll()
                }
            }
        }
        binding.placesToVisitRv.adapter = DiscoverPlacesAdapter(PlaceOnClickListener {
            // Create a Uri from an intent string. Use the result to create an Intent.
            val gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=${it.point.lat},${it.point.lon}")

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps")

            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent)

        })

        binding.fourRandomListRvDiscoverHome.adapter = DiscoverFeaturedAdapter(OnClickListener {
            discoverViewModel.onNavigateToTripDetails(it)
        })

        binding.swipeRefreshDiscoverHome.setOnRefreshListener {
            discoverViewModel.getAllTrips()
        }

        discoverViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                //stop loadning
                //show error
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        discoverViewModel.onNavigateToTripDetailsData.observe(viewLifecycleOwner) {
            it?.let { trip ->
                discoverViewModel.getTripSaveState()?.let { isSaved ->
                    val openTripDetailsIntent = Intent(activity, TripHolderActivity::class.java)
                    openTripDetailsIntent.putExtra(Keys.TRIP_DETAILS_KEY, trip)
                    openTripDetailsIntent.putExtra(Keys.IS_SAVED_KEY, isSaved)
                    startActivity(openTripDetailsIntent)

                    discoverViewModel.onDoneNavigationToTripDetails()
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        discoverViewModel.getAllTrips()
    }

}