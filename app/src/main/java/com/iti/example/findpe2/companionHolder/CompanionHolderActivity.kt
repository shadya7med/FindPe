package com.iti.example.findpe2.companionHolder

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.ActivityCompanionHolderBinding

class CompanionHolderActivity : AppCompatActivity() {


    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCompanionHolderBinding.inflate(LayoutInflater.from(this))
        navController =
            (supportFragmentManager.findFragmentById(R.id.companion_nav_host) as NavHostFragment)
                .findNavController()

        NavigationUI.setupActionBarWithNavController(this,navController)


        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}