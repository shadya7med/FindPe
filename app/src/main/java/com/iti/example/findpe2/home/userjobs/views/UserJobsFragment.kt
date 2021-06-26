package com.iti.example.findpe2.home.userjobs.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentUserJobsBinding
import com.iti.example.findpe2.home.bidsReceived.ReceivedBidActivity
import com.iti.example.findpe2.home.userjobs.viewmodels.UserJobsViewModel

class UserJobsFragment : Fragment() {
    private lateinit var binding: FragmentUserJobsBinding
    private lateinit var viewModel: UserJobsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserJobsBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this).get(UserJobsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val requestResultLauncher =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            }
        }

        binding.userJobRecyclerView.adapter = UserJobsAdapter(UserJobOnClickListener {
            val receivedBidIntent = Intent(
                context,
                ReceivedBidActivity::class.java
            )
            receivedBidIntent.putExtra(Keys.JOB_KEY, it)
            requestResultLauncher.launch(receivedBidIntent)
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserJobs()
    }

}