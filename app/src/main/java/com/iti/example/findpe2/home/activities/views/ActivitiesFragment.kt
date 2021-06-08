package com.iti.example.findpe2.home.activities.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.CategoryTripsActivity
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentActivitiesBinding
import com.iti.example.findpe2.home.activities.viewmodels.ActivitiesViewModel

class ActivitiesFragment : Fragment() {

    private lateinit var binding: FragmentActivitiesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesBinding.inflate(layoutInflater, container, false)
        val viewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToCategoryTrips.observe(viewLifecycleOwner, Observer { category ->
            category?.let {
                val categoryIntent = Intent(context, CategoryTripsActivity::class.java)
                categoryIntent.putExtra(Keys.CATEGORY_KEY, category)
                startActivity(categoryIntent)
                viewModel.displayCompleted()
            }
        })
        // Inflate the layout for this fragment
        return binding.root
    }

}