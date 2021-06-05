package com.iti.example.findpe2.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.iti.example.findpe2.R
import com.iti.example.findpe2.authentication.CreateAccountActivity
import com.iti.example.findpe2.databinding.ActivityHomeBinding
import com.iti.example.findpe2.databinding.HomeDrawerHeaderBinding
import com.iti.example.findpe2.network.NetworkActivity


class HomeActivity : AppCompatActivity() {

    lateinit var binder: ActivityHomeBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var homeDrawerLayout: DrawerLayout
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //refer to the Activity Binder
        binder = ActivityHomeBinding.inflate(layoutInflater)
        //get reference to the associated nav Controller
        // we need to get it through fragment manager as it will find the Host Fragment via container id
        //but binder.home_nav_..  will get the container itself not the currently contained fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        homeDrawerLayout = binder.homeDrawerLayout
        //link nav Controller with bottom nav View
        binder.homeBottomNavigation.setupWithNavController(navController)
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
        NavigationUI.setupWithNavController(binder.homeDrawerNavView, navController)
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
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        drawerHeaderBinder.homeViewModel = homeViewModel
        //add header view for the drawer navigation
        binder.homeDrawerNavView.addHeaderView(drawerHeaderBinder.root)
        //update the nav   header image with the user image
        Glide.with(this)
            .load(homeViewModel.userPhotoUrl.value)
            .placeholder(R.drawable.background_gradient)
            .circleCrop()
            .into(drawerHeaderBinder.userImageImgViewNavHeaderHome);

        binder.homeDrawerNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logoutFragmentHome -> {
                    homeViewModel.logout()
                    val signoutIntent = Intent(this@HomeActivity, CreateAccountActivity::class.java)
                    startActivity(signoutIntent)
                    finish()
                }
            }
            true
        }
        //Register broadcast receiver
        registerReceiver(receiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        //add root as Top Active View
        setContentView(binder.root)
    }

    /*options menu lifecycle*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
    }

    /*up Button */
    override fun onSupportNavigateUp(): Boolean {
        // modify behaviour
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (!checkInternet(context!!)){
                finish()
                startActivity(Intent(context, NetworkActivity::class.java))
            }
        }
        private fun checkInternet(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return (netInfo != null && netInfo.isConnected());
        }

    }


}