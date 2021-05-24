package com.iti.example.findpe.tripCheckout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.iti.example.findpe.R
import com.iti.example.findpe.constants.Keys
import com.iti.example.findpe.databinding.ActivityTripHolderBinding

class TripHolderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTripHolderBinding.inflate(layoutInflater)
        val tripId = intent.getIntExtra(Keys.TRIP_ID_KEY,0)
        val tripDetailsBundle = bundleOf(Keys.TRIP_ID_KEY to tripId)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.trip_holder_navHost_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.setGraph(R.navigation.trip_holder_nav_graph,tripDetailsBundle)
        setContentView(binding.root)
    }
}