package com.iti.example.findpe2.home.timeline.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemTimelineRvBinding
import com.iti.example.findpe2.pojos.TimelineSlot

class TimelineAdapter: ListAdapter<TimelineSlot, TimelineAdapter.TimelineViewHolder>(object : DiffUtil.ItemCallback<TimelineSlot>(){
    override fun areItemsTheSame(oldItem: TimelineSlot, newItem: TimelineSlot) = oldItem === newItem

    override fun areContentsTheSame(oldItem: TimelineSlot, newItem: TimelineSlot) = oldItem.id == newItem.id
}) {

    class TimelineViewHolder(var binding: ListItemTimelineRvBinding): RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun from(parent: ViewGroup): TimelineViewHolder =
                TimelineViewHolder(ListItemTimelineRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        fun bind(slot: TimelineSlot){
            binding.slot = slot
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TimelineViewHolder.from(parent)

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) = holder.bind(getItem(position))
}