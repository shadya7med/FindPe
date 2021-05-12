package com.iti.example.findpe.Home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.iti.example.findpe.R
import com.iti.example.findpe.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binder:ActivityHomeBinding
    lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //refer to the Activity Binder
        binder = ActivityHomeBinding.inflate(layoutInflater)
        //get reference to the associated nav Controller
        // we need to get it through fragment manager as it will find the Host Fragment via container id
        //but binder.home_nav_..  will get the container itself not the currently contained fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //link nav Controller with bottom nav View
        binder.homeBottomNavigation.setupWithNavController(navController)
        //enable options menu
        //options menu enabled directly for activites
        //setup the up Button for non-Top level destinations will change for up button automatically for non-Top level
        NavigationUI.setupActionBarWithNavController(this,navController)

        //add root as Top Active View
        setContentView(binder.root)
    }

    /*options menu lifecycle*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_options_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,navController)||super.onOptionsItemSelected(item)
    }

    /*up Button */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}