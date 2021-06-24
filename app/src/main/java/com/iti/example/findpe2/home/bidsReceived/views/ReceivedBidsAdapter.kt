package com.iti.example.findpe2.home.bidsReceived.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemJobOffersBinding
import com.iti.example.findpe2.databinding.ListItemReceivedBidBinding
import com.iti.example.findpe2.home.joboffers.views.JobOfferAdapter
import com.iti.example.findpe2.pojos.ReceivedBid
import com.iti.example.findpe2.pojos.SentJobRequest

class ReceivedBidsAdapter( private val clickListener: ReceivedBidOnClickListener): ListAdapter<ReceivedBid, ReceivedBidsAdapter.VH>(object : DiffUtil.ItemCallback<ReceivedBid>(){
    override fun areItemsTheSame(oldItem: ReceivedBid, newItem: ReceivedBid) = oldItem === newItem

    override fun areContentsTheSame(oldItem: ReceivedBid, newItem: ReceivedBid) = oldItem == newItem

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ListItemReceivedBidBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position), clickListener)


    class VH(val binding: ListItemReceivedBidBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(receivedBid: ReceivedBid, clickListener: ReceivedBidOnClickListener){
            binding.receivedBid = receivedBid
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}
class ReceivedBidOnClickListener(val clickListener: (ReceivedBid) -> Unit){
    fun onClick(receivedBid: ReceivedBid) = clickListener(receivedBid)
}