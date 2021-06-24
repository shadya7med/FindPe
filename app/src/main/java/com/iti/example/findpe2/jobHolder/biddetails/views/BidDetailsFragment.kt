package com.iti.example.findpe2.jobHolder.biddetails.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentBidDetailsBinding
import com.iti.example.findpe2.home.bidsOffers.BidActivity
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
        val job: com.iti.example.findpe2.pojos.Job? = arguments?.getParcelable(Keys.JOB_KEY)
        val sentBid: SentBid? = arguments?.getParcelable(Keys.SENT_BID_KEY)

        if (sentBid != null) {
            binding.bidDeleteBtn.visibility = View.VISIBLE
            binding.bidProposalText.setText(sentBid.proposal)
            binding.bidOfferedAmountText.setText(sentBid.offer.toString())

            if (sentBid.status != RequestStatus.WAITING.value) {
                binding.bidProposalText.isEnabled = false
                binding.bidOfferedAmountText.isEnabled = false
                binding.bidBtn.visibility = View.INVISIBLE
            }


        }

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
                    .child(job?.clientId ?: sentBid?.clientID!!)
                    .child(job?.jobID?.toString() ?: sentBid?.jobID.toString())
                    .child(id)
                    .setValue(
                        ReceivedBid(
                            id,
                            job?.clientId ?: sentBid?.clientID!!,
                            image,
                            name,
                            job?.jobID?.toLong() ?: sentBid?.jobID,
                            binding.bidProposalText.text.toString(),
                            binding.bidOfferedAmountText.text.toString().toLong(),
                        )
                    )
                database.child("SentBidOffers")
                    .child(id)
                    .child(job?.jobID?.toString() ?: sentBid?.jobID.toString())
                    .setValue(
                        SentBid(
                            job?.jobID?.toLong() ?: sentBid?.jobID,
                            job?.clientId ?: sentBid?.clientID,
                            job?.user?.name ?: sentBid?.name,
                            binding.bidProposalText.text.toString(),
                            binding.bidOfferedAmountText.text.toString().toLong(),
                            RequestStatus.WAITING.value
                        )
                    )
            }

            Toast.makeText(requireActivity().applicationContext, "bid Sent", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigateUp()

        }
        val activityType = requireActivity()
        if (activityType is JobActivityHolder) {
            activityType.setActionBarTitle("Bid Details")
        } else if (activityType is BidActivity) {
            activityType.setActionBarTitle("Bid Details")
        }

        binding.bidDeleteBtn.setOnClickListener {
            scope.launch {
                val id = FirebaseAuth.getInstance().currentUser?.uid!!
                setLoading()
                val database = FirebaseDatabase.getInstance().reference
                database.child("SentBidOffers")
                    .child(id)
                    .child(sentBid?.jobID.toString())
                    .removeValue()
                database.child("ReceivedBidOffers")
                    .child(sentBid?.clientID!!)
                    .child(id)
                    .removeValue()
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Request deleted",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    private fun setLoading() {
        binding.bidProgressBar.visibility = View.VISIBLE
        binding.bidViewGroup.setAllClickable(false)
    }
}
