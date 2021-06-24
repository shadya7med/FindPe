package com.iti.example.findpe2.home.bidsReceivedDetails.views

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentJobDetailBinding
import com.iti.example.findpe2.databinding.FragmentReceivedBidDetailsBinding
import com.iti.example.findpe2.home.bidsReceived.ReceivedBidActivity
import com.iti.example.findpe2.home.bidsReceivedDetails.viewmodels.ReceivedBidsDetailsViewModel
import com.iti.example.findpe2.home.bidsReceivedDetails.viewmodels.ReceivedBidsDetailsViewModelFactory
import com.iti.example.findpe2.home.jobrequests.RequestsActivity
import com.iti.example.findpe2.jobHolder.JobActivityHolder
import com.iti.example.findpe2.jobHolder.jobdetails.viewmodels.JobDetailViewModel
import com.iti.example.findpe2.jobHolder.jobdetails.viewmodels.JobDetailViewModelFactory
import com.iti.example.findpe2.jobHolder.jobdetails.views.JobDetailFragmentArgs
import com.iti.example.findpe2.jobHolder.jobdetails.views.JobDetailFragmentDirections
import com.iti.example.findpe2.pojos.ReceivedBid


class ReceivedBidDetailsFragment : Fragment() {

    lateinit var viewModel: ReceivedBidsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReceivedBidDetailsBinding.inflate(layoutInflater, container, false)
        val receivedBid = ReceivedBidDetailsFragmentArgs.fromBundle(requireArguments()).receivedBid

        val viewModelFactory = ReceivedBidsDetailsViewModelFactory(
            receivedBid
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ReceivedBidsDetailsViewModel::class.java)
//
//        val congratsDialog =
//            AlertDialog.Builder(requireActivity(), R.style.congratulation_dialog_theme)
//                .setCancelable(false)
//                .setTitle(requireActivity().application.resources.getString(R.string.congratulations))
//                .setMessage(requireActivity().application.resources.getString(R.string.chat_room_created_dialog))
//                .setPositiveButton(
//                    requireActivity().application.resources.getString(R.string.ok)
//                ) { dialog, id ->
//                    requireActivity().setResult(Activity.RESULT_OK)
//                    //finish holder activity
//                    requireActivity().finish()
//                    viewModel.openChatRoomCompleted()
//                }.create()
//        congratsDialog.setOnShowListener {
//            congratsDialog.getButton(AlertDialog.BUTTON_POSITIVE)
//                .setTextColor(resources.getColor(R.color.title_text_custom_color))
//        }

//        viewModel.navigateToChatRoom.observe(viewLifecycleOwner, {
//            it?.let {
//                if (it) {
//                    //show congrats dialog
//                    congratsDialog.show()
//                } else {
//                    Snackbar.make(
//                        requireActivity().findViewById(android.R.id.content),
//                        "Failed To create chat room",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                }
//
//            }
//        })

//        viewModel.navigateUp.observe(viewLifecycleOwner, {
//            it?.let {
//                findNavController().navigateUp()
//                viewModel.navigateUpCompleted()
//            }
//        })


        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        val activityType = requireActivity()

        if (activityType is ReceivedBidActivity) {
            activityType.setActionBarTitle("Bid Details")
        }
        return binding.root
    }


}

