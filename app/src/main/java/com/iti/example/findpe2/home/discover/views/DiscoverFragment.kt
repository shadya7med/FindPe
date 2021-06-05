package com.iti.example.findpe2.home.discover.views

import android.content.Intent
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


    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDiscoverBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        binding.discoverViewModel = discoverViewModel
        val navController = findNavController()
        discoverViewModel.onNavigateToSeeAllClicked.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    navController.navigate(ExploreFragmentDirections.actionExploreFragmentHomeToAllTripsFragment())
                    discoverViewModel.onDoneNavigationToSeeAll()
                }
            }
        }

        binding.featuredListRvDiscoverHome.adapter = DiscoverFeaturedAdapter(OnClickListener {
            discoverViewModel.onNavigateToTripDetails(it)
        })

        discoverViewModel.errorMsg.observe(viewLifecycleOwner) {
            it?.let {
                //stop loadning
                //show error
                Snackbar.make(requireActivity().findViewById(android.R.id.content), it, Snackbar.LENGTH_LONG).show()
            }
        }
        discoverViewModel.onNavigateToTripDetailsData.observe(viewLifecycleOwner) {
            it?.let {
                val openTripDetailsIntent = Intent(activity, TripHolderActivity::class.java)
                openTripDetailsIntent.putExtra(Keys.TRIP_DETAILS_KEY, it)
                openTripDetailsIntent.putExtra(Keys.IS_SAVED_KEY, true)
                startActivity(openTripDetailsIntent)

                discoverViewModel.onDoneNavigationToTripDetails()
            }
        }
        
        return binding.root
    }


}