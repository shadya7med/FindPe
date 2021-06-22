package com.iti.example.findpe2.jobHolder.browseJobs.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemJobBinding
import com.iti.example.findpe2.pojos.Job

class BrowseJobsAdapter(private val clickListener: JobOnClickListener): ListAdapter<Job, BrowseJobsAdapter.VH>(object : DiffUtil.ItemCallback<Job>(){
    override fun areItemsTheSame(oldItem: Job, newItem: Job) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Job, newItem: Job) = oldItem == newItem

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ListItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position), clickListener)


    class VH(val binding: ListItemJobBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(job: Job, clickListener: JobOnClickListener){
            binding.job = job
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}
class JobOnClickListener(val clickListener: (Job) -> Unit){
    fun onClick(job: Job) = clickListener(job)
}