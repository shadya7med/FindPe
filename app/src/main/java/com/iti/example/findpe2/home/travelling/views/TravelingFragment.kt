package com.iti.example.findpe2.home.travelling.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentTravelingBinding
import com.iti.example.findpe2.home.travelling.viewModels.TravellingViewModel
import com.iti.example.findpe2.tripCheckout.TripHolderActivity


class TravelingFragment : Fragment() {

    lateinit var binding: FragmentTravelingBinding
    lateinit var navController: NavController
    private lateinit var viewModel: TravellingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentTravelingBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(TravellingViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel


        binding.travellingRv.adapter = TravellingTripAdapter(OnClickListener {
            viewModel.navigateToTripDetails(it)
        })

        binding.swipeRefreshTravellingHome.setOnRefreshListener {
            viewModel.getTrips()
        }

        viewModel.selectedTrip.observe(viewLifecycleOwner, Observer {
            it?.let { trip ->
                viewModel.getTripSaveState()?.let { isSaved ->
                    val openTripHolderIntent = Intent(activity, TripHolderActivity::class.java)
                    //mimic trip id with 5
                    openTripHolderIntent.putExtra(Keys.TRIP_DETAILS_KEY, trip)
                    openTripHolderIntent.putExtra(Keys.IS_SAVED_KEY, isSaved)
                    startActivity(openTripHolderIntent)
                    viewModel.navigateToTripDetailsComplete()
                }
            }
        })



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binding.filterBtnTravelingHome.setOnClickListener {
            navController.navigate(TravelingFragmentDirections.actionTravelingFragmentHomeToFilterFragment())
        }
        // saved State handle is  a map for returning date between fragments
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String, Any>>(
            Keys.FULL_FILTER_MAP_KEY
        )?.observe(
            viewLifecycleOwner, Observer { result ->
                viewModel.filterTrips(result)
            })
    }

    override fun onStart() {
        super.onStart()
        //in order to always refresh the visible data
        viewModel.getTrips()
    }

    override fun onStop() {
        super.onStop()
        /*navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String, Any>>(
            Keys.FULL_FILTER_MAP_KEY
        )?.removeObservers(viewLifecycleOwner)*/

    }

}