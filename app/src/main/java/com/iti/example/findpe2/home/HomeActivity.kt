package com.iti.example.findpe2.home

import android.content.Intent
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
import com.iti.example.findpe2.R
import com.iti.example.findpe2.authentication.CreateAccountActivity
import com.iti.example.findpe2.databinding.ActivityHomeBinding
import com.iti.example.findpe2.databinding.HomeDrawerHeaderBinding


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var homeDrawerLayout: DrawerLayout
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        drawerHeaderBinder.homeViewModel = homeViewModel
        //add header view for the drawer navigation
        binding.homeDrawerNavView.addHeaderView(drawerHeaderBinder.root)
        //update the nav   header image with the user image
        /*Glide.with(this)
            .load(homeViewModel.userPhotoUrl.value)
            .placeholder(R.drawable.background_gradient)
            .circleCrop()
            .into(drawerHeaderBinder.userImageImgViewNavHeaderHome)*/

        binding.homeDrawerNavView.setNavigationItemSelectedListener {
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
        //add root as Top Active View
        setContentView(binding.root)
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
}