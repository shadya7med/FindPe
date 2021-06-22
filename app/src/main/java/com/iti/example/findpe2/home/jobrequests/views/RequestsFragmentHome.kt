package com.iti.example.findpe2.home.jobrequests.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iti.example.findpe2.databinding.FragmentRequestsHomeBinding
import com.iti.example.findpe2.home.jobrequests.RequestsActivity
import com.iti.example.findpe2.home.jobrequests.viewmodels.RequestsHomeViewModel

class RequestsFragmentHome : Fragment() {

    lateinit var binding: FragmentRequestsHomeBinding
    lateinit var viewModel: RequestsHomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsHomeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(RequestsHomeViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.recyclerView.adapter = RequestsAdapter(RequestOnClickListener {
            findNavController().navigate(RequestsFragmentHomeDirections.actionRequestsFragmentHomeToJobDetailFragment(it, null))
        })
        setHasOptionsMenu(true)
        (requireActivity() as RequestsActivity).setActionBarTitle("Requests")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRequests()
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