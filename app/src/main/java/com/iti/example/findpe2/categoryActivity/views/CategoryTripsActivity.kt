package com.iti.example.findpe2.categoryActivity.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.R
import com.iti.example.findpe2.categoryActivity.viewHolder.CategoryViewModel
import com.iti.example.findpe2.categoryActivity.viewHolder.CategoryViewModelFactory
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.ActivityCategoryTripsBinding
import com.iti.example.findpe2.home.travelling.views.OnClickListener
import com.iti.example.findpe2.home.travelling.views.TravellingTripAdapter
import com.iti.example.findpe2.tripCheckout.TripHolderActivity
import com.iti.example.findpe2.utils.Category

class CategoryTripsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryTripsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getStringExtra(Keys.CATEGORY_KEY)
        binding = ActivityCategoryTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //make up button appear in top destination fragment
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = category
        val categoryItem: Category = when(category){
            "nature" -> Category.NATURE
            "cultural" -> Category.CULTURAL
            "modern life" -> Category.MODERN
            "Popularity" -> Category.POPULARITY
            else -> Category.SEA
        }
        val viewModelFactory = CategoryViewModelFactory(categoryItem.name, categoryItem.id)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CategoryViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.categoryTripRv.adapter = TravellingTripAdapter(OnClickListener {
                viewModel.navigateToTripDetails(it)
        })
        viewModel.selectedTrip.observe(this, Observer {
            it?.let {
                val tripDetailsIntent = Intent(this, TripHolderActivity::class.java)
                tripDetailsIntent.putExtra(Keys.TRIP_DETAILS_KEY, it)
                startActivity(tripDetailsIntent)
                viewModel.navigateToTripDetailsComplete()
            }
        })




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}