package com.iti.example.findpe2.jobHolder.biddetails.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentBidDetailsBinding
import com.iti.example.findpe2.jobHolder.JobActivityHolder
import com.iti.example.findpe2.jobHolder.jobdetails.views.JobDetailFragmentArgs
import com.iti.example.findpe2.pojos.ReceivedBid
import com.iti.example.findpe2.pojos.RequestStatus
import com.iti.example.findpe2.pojos.SentBid
import com.iti.example.findpe2.pojos.SentJobRequest
import com.iti.example.findpe2.utils.setAllClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BidDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBidDetailsBinding
    private lateinit var coroutinejob: Job
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBidDetailsBinding.inflate(layoutInflater, container, false)
        val job = BidDetailsFragmentArgs.fromBundle(requireArguments()).job

        coroutinejob = Job()
        val scope = CoroutineScope(Dispatchers.Main + coroutinejob)
        binding.bidBtn.setOnClickListener {
            binding.bidProposalText.apply {
                if (text.isEmpty()) {
                    error = "Please enter job proposal"
                    requestFocus()
                    return@setOnClickListener
                }
            }

            binding.bidOfferedAmountText.apply {
                if (text.isEmpty()) {
                    error = "Please enter job price"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            scope.launch {
                setLoading()
                val id = FirebaseAuth.getInstance().currentUser?.uid!!
                val name = FirebaseAuth.getInstance().currentUser?.displayName!!
                val image = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()


                val database = FirebaseDatabase.getInstance().reference
                database.child("ReceivedBidOffers")
                    .child(job.clientId)
                    .child(id)
                    .setValue(
                        ReceivedBid(
                            id,
                            name,
                            image,
                            binding.bidProposalText.text.toString(),
                            binding.bidOfferedAmountText.text.toString().toLong(),
                        )
                    )
                database.child("SentBidOffers")
                    .child(id)
                    .child(job.jobID.toString())
                    .setValue(
                        SentBid(
                            job.jobID?.toLong(),
                            job.clientId,
                            job.user?.name,
                            binding.bidProposalText.text.toString(),
                            binding.bidOfferedAmountText.text.toString().toLong(),
                            RequestStatus.WAITING.value
                        )
                    )
            }

            Toast.makeText(requireActivity().applicationContext, "bid Sent", Toast.LENGTH_SHORT).show()
            requireActivity().finish()


        }
        val activityType = requireActivity()
        if (activityType is JobActivityHolder) {
            activityType.setActionBarTitle("Bid Details")
        }

        return binding.root
    }
    private fun setLoading() {
        binding.bidProgressBar.visibility = View.VISIBLE
        binding.bidViewGroup.setAllClickable(false)
    }
}
