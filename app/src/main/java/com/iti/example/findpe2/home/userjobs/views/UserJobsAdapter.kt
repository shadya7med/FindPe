package com.iti.example.findpe2.home.userjobs.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemUserJobBinding


class UserJobsAdapter (private val clickListener: UserJobOnClickListener): ListAdapter<String, UserJobsAdapter.VH>(object : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem === newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ListItemUserJobBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position), clickListener)


    class VH(val binding: ListItemUserJobBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(jobID: String, clickListener: UserJobOnClickListener){
            binding.jobID = jobID
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}
class UserJobOnClickListener(val clickListener: (String) -> Unit){
    fun onClick(jobId: String) = clickListener(jobId)
}