package com.iti.example.findpe2.home.travelling.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    lateinit var binder: FragmentTravelingBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binder = FragmentTravelingBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(TravellingViewModel::class.java)

        binder.lifecycleOwner = this

        binder.viewModel = viewModel


        binder.travellingRv.adapter = TravellingTripAdapter(OnClickListener {
            viewModel.navigateToTripDetails(it)
        })

        viewModel.selectedTrip.observe(viewLifecycleOwner, Observer {
            it?.let {
                val openTripHolderIntent = Intent(activity, TripHolderActivity::class.java)
                //mimic trip id with 5
                openTripHolderIntent.putExtra("trip", it)
                startActivity(openTripHolderIntent)
                viewModel.navigateToTripDetailsComplete()
            }
        })



        return binder.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binder.filterBtnTravelingHome.setOnClickListener {
            navController.navigate(TravelingFragmentDirections.actionTravelingFragmentHomeToFilterFragment())
        }
        // saved State handle is  a map for returning date between fragments
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String, Any>>(
            Keys.FULL_FILTER_MAP_KEY
        )?.observe(
            viewLifecycleOwner, Observer { result ->
                Log.i("FiTrav", "${result[Keys.MIN_RANGE_KEY] as Double}")
            })
    }

    override fun onStop() {
        super.onStop()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String, Any>>(
            Keys.FULL_FILTER_MAP_KEY
        )?.removeObservers(viewLifecycleOwner)

    }

}