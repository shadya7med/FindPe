package com.iti.example.findpe2.home.jobrequests.views
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iti.example.findpe2.databinding.ListItemRequestBinding
import com.iti.example.findpe2.pojos.ReceivedJobRequest

class RequestsAdapter(private val clickListener: RequestOnClickListener): ListAdapter<ReceivedJobRequest, RequestsAdapter.VH>(object : DiffUtil.ItemCallback<ReceivedJobRequest>(){
    override fun areItemsTheSame(oldItem: ReceivedJobRequest, newItem: ReceivedJobRequest) = oldItem === newItem

    override fun areContentsTheSame(oldItem: ReceivedJobRequest, newItem: ReceivedJobRequest) = oldItem == newItem

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ListItemRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position), clickListener)


    class VH(val binding: ListItemRequestBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(request: ReceivedJobRequest, clickListener: RequestOnClickListener){
            binding.request = request
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}
class RequestOnClickListener(val clickListener: (ReceivedJobRequest) -> Unit){
    fun onClick(request: ReceivedJobRequest) = clickListener(request)
}