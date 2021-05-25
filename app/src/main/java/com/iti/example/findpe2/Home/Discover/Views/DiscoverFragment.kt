package com.iti.example.findpe2.Home.Discover.Views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentDiscoverBinding
import com.iti.example.findpe2.tripCheckout.TripHolderActivity


class DiscoverFragment : Fragment() {

    lateinit var binding:FragmentDiscoverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverBinding.inflate(inflater,parent,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.discoverSeeAllButton.setOnClickListener {
            val openTripHolderIntent = Intent(activity,TripHolderActivity::class.java)
            //mimic trip id with 5
            openTripHolderIntent.putExtra(Keys.TRIP_ID_KEY,5)
            startActivity(openTripHolderIntent)
        }
    }

}