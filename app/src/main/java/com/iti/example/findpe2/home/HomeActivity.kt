package com.iti.example.findpe2.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.iti.example.findpe2.HomeNavGraphDirections
import com.iti.example.findpe2.R
import com.iti.example.findpe2.authentication.CreateAccountActivity
import com.iti.example.findpe2.constants.Constants.Companion.MY_PERMISSIONS_REQUEST_LOCATION
import com.iti.example.findpe2.databinding.ActivityHomeBinding
import com.iti.example.findpe2.databinding.HomeDrawerHeaderBinding
import com.iti.example.findpe2.home.bidsOffers.BidActivity
import com.iti.example.findpe2.home.joboffers.JobOffersActivity
import com.iti.example.findpe2.home.jobrequests.RequestsActivity
import com.iti.example.findpe2.network.NetworkActivity


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var homeDrawerLayout: DrawerLayout
    lateinit var homeViewModel: HomeViewModel
    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //get location provider
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        //refer to the Activity Binder
        binding = ActivityHomeBinding.inflate(layoutInflater)
        //get reference to the associated nav Controller
        // we need to get it through fragment manager as it will find the Host Fragment via container id
        //but binder.home_nav_..  will get the container itself not the currently contained fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        homeDrawerLayout = binding.homeDrawerLayout
        //link nav Controller with bottom nav View
        binding.homeBottomNavigation.setupWithNavController(navController)
        //enable options menu
        //options menu enabled directly for activites

        //create a set of Top level destinations
        val topLevelDestinationsSet = setOf(
            R.id.exploreFragmentHome,
            R.id.chatFragmentHome,
            R.id.companionFragmentHome,
            R.id.savedFragmentHome,
            R.id.travelingFragmentHome
        )
        //configure the Top-Level destinations with DrawerLayout
        appBarConfiguration = AppBarConfiguration(topLevelDestinationsSet, homeDrawerLayout)
        //setup the up Button for non-Top level destinations will change for up button automatically for non-Top level
        //hook DrawerLayout with the navController via NavigationUI --> modify View
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        //hook navigationView with the navController via NavigationUI
        NavigationUI.setupWithNavController(binding.homeDrawerNavView, navController)
        //lock swipe behaviour except for home
        /*navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _ ->
            if (nd.id == nc.graph.startDestination) {
                homeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                homeDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }*/
        //inflate the drawer header layout
        val drawerHeaderBinder = HomeDrawerHeaderBinding.inflate(layoutInflater)
        drawerHeaderBinder.lifecycleOwner = this
        homeViewModel = ViewModelProvider(
            this, HomeViewModelFactory(
                fusedLocationProvider,
                application
            )
        ).get(HomeViewModel::class.java)
        drawerHeaderBinder.homeViewModel = homeViewModel
        //add header view for the drawer navigation
        binding.homeDrawerNavView.addHeaderView(drawerHeaderBinder.root)
        //update the nav   header image with the user image
        /*Glide.with(this)
            .load(homeViewModel.userPhotoUrl.value)
            .placeholder(R.drawable.background_gradient)
            .circleCrop()
            .into(drawerHeaderBinder.userImageImgViewNavHeaderHome)*/
        homeViewModel.onNavigateToProfile.observe(this) {
            it?.let {
                if (it) {
                    navController.navigate(
                        HomeNavGraphDirections.actionGlobalProfileFragment(
                            false,
                            homeViewModel.getUserAlsoCompanion(),
                            homeViewModel.getIsUserAlsoCompanion()
                        )
                    )
                    binding.homeDrawerLayout.close()
                    homeViewModel.onDoneNavigationToProfile()
                }
            }
        }
        homeViewModel.companionSecVisibility.observe(this, Observer {
            it?.let {
                binding.homeDrawerNavView.menu.setGroupVisible(R.id.home_drawer_group_companion, it)
            }
        })

        val requestsActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    binding.homeDrawerLayout.close()
                    //chat room added successfully
                    navController.navigate(HomeNavGraphDirections.actionGlobalChatFragmentHome())
                }
            }

        binding.homeDrawerNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logoutFragmentHome -> {
                    homeViewModel.logout()
                    val signoutIntent = Intent(this@HomeActivity, CreateAccountActivity::class.java)
                    startActivity(signoutIntent)
                    finish()
                }
                R.id.RequestsFragmentHome -> {
                    val requestIntent = Intent(this@HomeActivity, RequestsActivity::class.java)
                    requestsActivityLauncher.launch(requestIntent)
                }
                R.id.jobsFragmentHome -> {
                    val jobOffersIntent = Intent(this@HomeActivity, JobOffersActivity::class.java)
                    requestsActivityLauncher.launch(jobOffersIntent)
                }
                R.id.Bid -> {
                    startActivity(Intent(this@HomeActivity, BidActivity::class.java))
                }
            }
            true
        }
        //Register broadcast receiver
        registerReceiver(receiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        //request location permission
        checkLocationPermission()
        //add root as Top Active View
        setContentView(binding.root)
    }

    /*options menu lifecycle*/
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            navController
        ) || super.onOptionsItemSelected(
            item
        )
    }*/

    /*up Button */
    override fun onSupportNavigateUp(): Boolean {
        // modify behaviour
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        if (checkLocationService()) homeViewModel.startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        homeViewModel.stopLocationUpdates()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (!checkInternet(context!!)) {
                finishAffinity()
                startActivity(Intent(context, NetworkActivity::class.java))
            }
        }

        private fun checkInternet(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return (netInfo != null && netInfo.isConnected());
        }

    }

    //Location
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    }
                    .create()
                    .apply {
                        setOnShowListener {
                            this.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                                application.resources.getColor(
                                    R.color.title_text_custom_color
                                )
                            )
                        }
                    }
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()
            }
        }
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    //check for location service
                    if (checkLocationService()) {
                        //permission and service enabled open map with the companion location
                        //homeViewModel.saveCurrentCompanionLocation()
                        //Update from onResume
                    } else {
                        openLocationSettings()
                    }
                    Log.i("Home", "permission granted")

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(
                        this,
                        "permission denied other users will not be able to locate you on their maps",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    private fun checkLocationService(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //check location service enabled or not
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
        return false
    }

    private fun openLocationSettings() {
        AlertDialog.Builder(this)
            .setTitle("Location Service Needed")
            .setMessage("This app needs the Location service to be enabled to use the Show companion location feature")
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                //Prompt the user once explanation has been shown
                //open location settings
                val openLocationSettingsIntent =
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(openLocationSettingsIntent)
            }
            .create()
            .apply {
                setOnShowListener {
                    this.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        application.resources.getColor(
                            R.color.title_text_custom_color
                        )
                    )
                }
            }
            .show()
    }

}