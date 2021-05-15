package com.iti.example.findpe.home.traveling.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe.databinding.FragmentTravelingBinding
import com.iti.example.findpe.home.filter.viewModels.FilterViewModel


class TravelingFragment : Fragment() {

    lateinit var binder:FragmentTravelingBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binder = FragmentTravelingBinding.inflate(inflater,container,false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binder.filterBtnTravelingHome.setOnClickListener {
            navController.navigate(TravelingFragmentDirections.actionTravelingFragmentHomeToFilterFragment())
        }
        // saved State handle is  a map for returning date between fragments
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String,Any>>(FilterViewModel.FULL_MAP_KEY)?.observe(
            viewLifecycleOwner) { result ->
            // Do something with the result.
            Log.i("FiTrav", "${result[FilterViewModel.TO_DATE_KEY] as Long}")
        }
    }


}