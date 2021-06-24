package com.iti.example.findpe2.home.bidsOffers.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemBidOfferBinding
import com.iti.example.findpe2.databinding.ListItemJobOffersBinding
import com.iti.example.findpe2.pojos.SentBid
import com.iti.example.findpe2.pojos.SentJobRequest

class BidOfferAdapter ( private val clickListener: BidOnClickListener): ListAdapter<SentBid, BidOfferAdapter.VH>(object : DiffUtil.ItemCallback<SentBid>(){
    override fun areItemsTheSame(oldItem: SentBid, newItem: SentBid) = oldItem === newItem

    override fun areContentsTheSame(oldItem: SentBid, newItem: SentBid) = oldItem == newItem

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ListItemBidOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position), clickListener)


    class VH(val binding: ListItemBidOfferBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(request: SentBid, clickListener: BidOnClickListener){
            binding.bidOffer = request
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}
class BidOnClickListener(val clickListener: (SentBid) -> Unit){
    fun onClick(bid: SentBid) = clickListener(bid)
}