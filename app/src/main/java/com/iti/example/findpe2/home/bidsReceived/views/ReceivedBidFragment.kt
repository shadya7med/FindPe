package com.iti.example.findpe2.home.bidsReceived.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentReceivedBidBinding
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
            ReceivedBidViewModelFactory(13)
        ).get(ReceivedBidViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.receivedBidRecyclerView.adapter = ReceivedBidsAdapter(ReceivedBidOnClickListener {

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRequests()
    }
}