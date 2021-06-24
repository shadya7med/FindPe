package com.iti.example.findpe2.home.bidsReceived

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.ActivityJobHolderBinding
import com.iti.example.findpe2.databinding.ActivityReceivedBidBinding

class ReceivedBidActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityReceivedBidBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.received_bid_nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        //make up button appear in top destination fragment
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
    fun setActionBarTitle(title: String){
        val actionBar = supportActionBar
        actionBar?.title = title
    }
}