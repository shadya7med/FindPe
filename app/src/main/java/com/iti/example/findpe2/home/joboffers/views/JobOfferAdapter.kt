package com.iti.example.findpe2.home.joboffers.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemJobOffersBinding
import com.iti.example.findpe2.pojos.SentJobRequest

class JobOfferAdapter( private val clickListener: JobRequestOnClickListener): ListAdapter<SentJobRequest, JobOfferAdapter.VH>(object : DiffUtil.ItemCallback<SentJobRequest>(){
    override fun areItemsTheSame(oldItem: SentJobRequest, newItem: SentJobRequest) = oldItem === newItem

    override fun areContentsTheSame(oldItem: SentJobRequest, newItem: SentJobRequest) = oldItem == newItem

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ListItemJobOffersBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position), clickListener)


    class VH(val binding: ListItemJobOffersBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(request: SentJobRequest, clickListener: JobRequestOnClickListener){
            binding.jobOffer = request
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}
class JobRequestOnClickListener(val clickListener: (SentJobRequest) -> Unit){
    fun onClick(request: SentJobRequest) = clickListener(request)
}