package com.iti.example.findpe2.jobHolder.jobdetails.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentJobDetailBinding
import com.iti.example.findpe2.home.jobrequests.RequestsActivity
import com.iti.example.findpe2.jobHolder.JobActivityHolder
import com.iti.example.findpe2.jobHolder.jobdetails.viewmodels.JobDetailViewModel
import com.iti.example.findpe2.jobHolder.jobdetails.viewmodels.JobDetailViewModelFactory

class JobDetailFragment : Fragment() {

    lateinit var binding: FragmentJobDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobDetailBinding.inflate(layoutInflater, container, false)
        val args by navArgs<JobDetailFragmentArgs>()

        val viewModelFactory = JobDetailViewModelFactory(args.request, args.job)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(JobDetailViewModel::class.java)

        viewModel.navigateToChatRoom.observe(viewLifecycleOwner, {
            it?.let {
                //open chat here

                viewModel.openChatRoomCompleted()
            }
        })

        viewModel.navigateUp.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigateUp()
                viewModel.navigateUpCompleted()
            }
        })
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val activityType = requireActivity()

        if(activityType is RequestsActivity){
            activityType.setActionBarTitle("Details")
        }else if(activityType is JobActivityHolder){
            activityType.setActionBarTitle("Details")
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.job_details_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.preview_client -> {

                //show client Details hereee
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}