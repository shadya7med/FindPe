package com.iti.example.findpe2.jobHolder.browseJobs.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentBrowseJobsBinding
import com.iti.example.findpe2.jobHolder.JobActivityHolder
import com.iti.example.findpe2.jobHolder.browseJobs.viewmodels.BrowseJobsViewModel


class BrowseJobsFragment : Fragment() {
    lateinit var binding: FragmentBrowseJobsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrowseJobsBinding.inflate(layoutInflater, container, false)

        binding.jobRecyclerview.adapter = BrowseJobsAdapter(JobOnClickListener { job ->
            findNavController().navigate(BrowseJobsFragmentDirections.actionBrowseJobsFragmentToJobDetailFragment3(null , job))
        })
        (requireActivity() as JobActivityHolder).setActionBarTitle("Jobs")

        val viewModel = ViewModelProvider(this).get(BrowseJobsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        requireActivity().actionBar?.title = "Jobs"

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}