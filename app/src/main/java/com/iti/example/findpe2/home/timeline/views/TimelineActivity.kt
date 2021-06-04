package com.iti.example.findpe2.home.timeline.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.iti.example.findpe2.databinding.ActivityTimelineBinding
import com.iti.example.findpe2.home.timeline.viewmodels.TimelineViewModel
import com.iti.example.findpe2.home.timeline.viewmodels.TimelineViewModelFactory

class TimelineActivity : AppCompatActivity() {
    private lateinit var viewModel: TimelineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTimelineBinding.inflate(layoutInflater)
        //get nav args using delegation
        val arguments by navArgs<TimelineActivityArgs>()
        val viewModelFactory = TimelineViewModelFactory(arguments.id)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TimelineViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.timelineRv.adapter = TimelineAdapter()

        viewModel.closeActivity.observe(this, Observer { close ->
            if(close){
                finish()
                viewModel.closeComplete()
            }
        })
        setContentView(binding.root)
    }
}