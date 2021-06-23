package com.iti.example.findpe2.jobHolder.jobdetails.views

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentJobDetailBinding
import com.iti.example.findpe2.home.jobrequests.RequestsActivity
import com.iti.example.findpe2.jobHolder.JobActivityHolder
import com.iti.example.findpe2.jobHolder.jobdetails.viewmodels.JobDetailViewModel
import com.iti.example.findpe2.jobHolder.jobdetails.viewmodels.JobDetailViewModelFactory

class JobDetailFragment : Fragment() {

    lateinit var viewModel: JobDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentJobDetailBinding.inflate(layoutInflater, container, false)


        val viewModelFactory = JobDetailViewModelFactory(
            JobDetailFragmentArgs.fromBundle(requireArguments()).request,
            JobDetailFragmentArgs.fromBundle(requireArguments()).job
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(JobDetailViewModel::class.java)

        val congratsDialog =
            AlertDialog.Builder(requireActivity(), R.style.congratulation_dialog_theme)
                .setCancelable(false)
                .setTitle(requireActivity().application.resources.getString(R.string.congratulations))
                .setMessage(requireActivity().application.resources.getString(R.string.chat_room_created_dialog))
                .setPositiveButton(
                    requireActivity().application.resources.getString(R.string.ok)
                ) { dialog, id ->
                    requireActivity().setResult(Activity.RESULT_OK)
                    //finish holder activity
                    requireActivity().finish()
                    viewModel.openChatRoomCompleted()
                }.create()
        congratsDialog.setOnShowListener {
            congratsDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.title_text_custom_color))
        }

        viewModel.navigateToChatRoom.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    //show congrats dialog
                    congratsDialog.show()
                } else {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "Failed To create chat room",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

            }
        })

        viewModel.navigateUp.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigateUp()
                viewModel.navigateUpCompleted()
            }
        })
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val activityType = requireActivity()

        if (activityType is RequestsActivity) {
            activityType.setActionBarTitle("Details")
        } else if (activityType is JobActivityHolder) {
            activityType.setActionBarTitle("Details")
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.job_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.preview_client -> {
                viewModel.getClientID()?.let {
                    findNavController().navigate(
                        JobDetailFragmentDirections
                            .actionJobDetailFragmentToPreviewClientProfileFragment(
                                it
                            )
                    )
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}