package com.iti.example.findpe2.home.timeline.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentTimelineBinding
import com.iti.example.findpe2.home.timeline.viewmodels.TimelineViewModel
import com.iti.example.findpe2.home.timeline.viewmodels.TimelineViewModelFactory

class TimelineFragment : Fragment() {

    private lateinit var viewModel: TimelineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTimelineBinding.inflate(layoutInflater, container, false)
        //get nav args using delegation
        val arguments by navArgs<TimelineFragmentArgs>()
        val viewModelFactory = TimelineViewModelFactory(arguments.id)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TimelineViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.timelineRv.adapter = TimelineAdapter()
        return binding.root
    }

}