package com.iti.example.findpe2.home.discover.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemPlacesRvBinding
import com.iti.example.findpe2.pojos.PlaceToVisit

class DiscoverPlacesAdapter (private val clickListener: PlaceOnClickListener) :
    ListAdapter<PlaceToVisit, DiscoverPlacesAdapter.ViewHolder>(object : DiffUtil.ItemCallback<PlaceToVisit>() {
        override fun areItemsTheSame(oldItem: PlaceToVisit, newItem: PlaceToVisit) = oldItem === newItem

        override fun areContentsTheSame(oldItem: PlaceToVisit, newItem: PlaceToVisit) = oldItem.xid == newItem.xid
    }) {
    class ViewHolder(var binding: ListItemPlacesRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceToVisit, clickListener: PlaceOnClickListener) {
            binding.place = place
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ListItemPlacesRvBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)
}
class PlaceOnClickListener(var clickListener: (PlaceToVisit) -> Unit){
    fun onClick(place: PlaceToVisit) = clickListener(place)
}