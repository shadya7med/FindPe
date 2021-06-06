package com.iti.example.findpe2.home.companionsList.views


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemCompanionInfoBinding
import com.iti.example.findpe2.pojos.Companion


class CompanionListAdapter(private val companionListClickListener: CompanionListClickListener) :
    ListAdapter<Companion, CompanionListAdapter.CompanionListViewHolder>(CompanionListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompanionListViewHolder.from(parent)

    override fun onBindViewHolder(holder: CompanionListViewHolder, position: Int) =
        holder.bind(getItem(position),companionListClickListener)

    class CompanionListViewHolder(val binding: ListItemCompanionInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CompanionListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCompanionInfoBinding.inflate(layoutInflater, parent, false)
                return CompanionListViewHolder(binding)
            }
        }

        fun bind(currentItem: com.iti.example.findpe2.pojos.Companion,companionListClickListener: CompanionListClickListener) {
            binding.companion = currentItem
            binding.companionClickListener = companionListClickListener
            binding.executePendingBindings()
        }

    }

    class CompanionListDiffCallback : DiffUtil.ItemCallback<Companion>() {
        override fun areItemsTheSame(oldItem: Companion, newItem: Companion) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Companion, newItem: Companion) = oldItem == newItem
    }
    class CompanionListClickListener(val clickListener:(Companion)->Unit){
        fun onClick(companion: Companion) = clickListener(companion)
    }

}