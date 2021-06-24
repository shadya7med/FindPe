package com.iti.example.findpe2.home.bidsOffers.views

import android.content.Intent
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
import com.iti.example.findpe2.databinding.FragmentBidMainBinding
import com.iti.example.findpe2.home.bidsOffers.BidActivity
import com.iti.example.findpe2.home.bidsOffers.viewmodels.BidMainViewModel
import com.iti.example.findpe2.jobsendrequest.views.JobRequestActivity

class BidMainFragment : Fragment() {

    lateinit var binding: FragmentBidMainBinding
    lateinit var viewModel: BidMainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBidMainBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(
            BidMainViewModel::
            class.java
        )

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.bidOffersRecyclerView.adapter = BidOfferAdapter(BidOnClickListener
        {
            val bundle = Bundle()
            bundle.putParcelable(Keys.SENT_BID_KEY, it)
            findNavController().navigate(R.id.action_bidMainFragment_to_bidDetailsFragment2, bundle)
        })
        setHasOptionsMenu(true)
        (requireActivity() as BidActivity).setActionBarTitle("Bids")
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