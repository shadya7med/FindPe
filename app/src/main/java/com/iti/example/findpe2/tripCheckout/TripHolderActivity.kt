package com.iti.example.findpe2.tripCheckout

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.ActivityTripHolderBinding
import com.iti.example.findpe2.pojos.Trip

class TripHolderActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTripHolderBinding.inflate(layoutInflater)
        val trip = intent.getParcelableExtra<Trip>("trip")
        val tripDetailsBundle = bundleOf("trip" to trip)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.trip_holder_navHost_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        navController.setGraph(R.navigation.trip_holder_nav_graph,tripDetailsBundle)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = null
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}