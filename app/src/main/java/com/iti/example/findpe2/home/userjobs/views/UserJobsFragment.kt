package com.iti.example.findpe2.home.userjobs.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentUserJobsBinding
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

        binding.userJobRecyclerView.adapter = UserJobsAdapter(UserJobOnClickListener {


        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserJobs()
    }

}