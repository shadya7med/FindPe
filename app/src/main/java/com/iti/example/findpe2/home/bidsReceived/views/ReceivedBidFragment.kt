package com.iti.example.findpe2.home.bidsReceived.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentReceivedBidBinding
import com.iti.example.findpe2.home.bidsOffers.BidActivity
import com.iti.example.findpe2.home.bidsReceived.ReceivedBidActivity
import com.iti.example.findpe2.home.bidsReceived.viewmodels.ReceivedBidViewModel
import com.iti.example.findpe2.home.bidsReceived.viewmodels.ReceivedBidViewModelFactory

class ReceivedBidFragment : Fragment() {
    private lateinit var binding: FragmentReceivedBidBinding
    private lateinit var viewModel: ReceivedBidViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReceivedBidBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(
            this,
            ReceivedBidViewModelFactory(requireActivity().intent.getStringExtra(Keys.JOB_KEY) ?: "")
        ).get(ReceivedBidViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.receivedBidRecyclerView.adapter = ReceivedBidsAdapter(ReceivedBidOnClickListener {
            findNavController().navigate(ReceivedBidFragmentDirections.actionReceivedBidFragmentToReceivedBidDetailsFragment(it))
        })
        setHasOptionsMenu(true)
        (requireActivity() as ReceivedBidActivity).setActionBarTitle("Job Bids")
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


    override fun onResume() {
        super.onResume()
        viewModel.getRequests()
    }
}