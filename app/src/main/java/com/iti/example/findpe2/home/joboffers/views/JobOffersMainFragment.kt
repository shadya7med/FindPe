package com.iti.example.findpe2.home.joboffers.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentJobOffersMainBinding
import com.iti.example.findpe2.home.joboffers.JobOffersActivity
import com.iti.example.findpe2.home.joboffers.viewmodels.JobOffersMainViewModel
import com.iti.example.findpe2.jobsendrequest.views.JobRequestActivity

class JobOffersMainFragment : Fragment() {
    lateinit var binding: FragmentJobOffersMainBinding
    lateinit var viewModel: JobOffersMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobOffersMainBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(JobOffersMainViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.recyclerView.adapter = JobOfferAdapter(JobRequestOnClickListener {
            val jobOfferIntent = Intent(context, JobRequestActivity::class.java)
            jobOfferIntent.putExtra(Keys.JOB_OFFER, it)
            startActivity(jobOfferIntent)
        })
        setHasOptionsMenu(true)
        (requireActivity() as JobOffersActivity).setActionBarTitle("Offers")
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        viewModel.getRequests()
    }
}