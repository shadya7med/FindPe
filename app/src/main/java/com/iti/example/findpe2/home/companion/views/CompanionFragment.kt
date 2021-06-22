package com.iti.example.findpe2.home.companion.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iti.example.findpe2.companionHolder.CompanionHolderActivity
import com.iti.example.findpe2.databinding.FragmentCompanionBinding
import com.iti.example.findpe2.home.companion.viewModels.CompanionViewModel
import com.iti.example.findpe2.jobHolder.JobActivityHolder
import com.iti.example.findpe2.jobsendrequest.views.JobRequestActivity

class CompanionFragment : Fragment() {
    lateinit var companionViewModel: CompanionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentCompanionBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner

        companionViewModel = ViewModelProvider(this).get(CompanionViewModel::class.java)
        binding.companionViewModel = companionViewModel

        val navController = findNavController()

        companionViewModel.onNavigateToBeCompanionHolderData.observe(viewLifecycleOwner){
            it?.let{
                if (it){
                    val openCompanionHolderIntent = Intent(activity,CompanionHolderActivity::class.java)
                    startActivity(openCompanionHolderIntent)

                    companionViewModel.onDoneNavigationToBeCompanionHolder()
                }
            }
        }

        companionViewModel.onNavigateToCompanionListData.observe(viewLifecycleOwner){
            it?.let{
                if(it){
                    navController.navigate(CompanionFragmentDirections.actionCompanionFragmentHomeToCompanionListFragment())
                    companionViewModel.onDoneNavigationToCompanionList()
                }
            }
        }
        companionViewModel.navigateToJobRequest.observe(viewLifecycleOwner){
            it?.let {
                val intent = Intent(activity, JobRequestActivity::class.java)
                startActivity(intent)
                companionViewModel.displayJobRequestActivityCompleted()
            }
        }
        companionViewModel.navigateToBrowseJob.observe(viewLifecycleOwner){
            it?.let {
                val intent = Intent(activity,JobActivityHolder::class.java)
                startActivity(intent)
                companionViewModel.displayBrowseJobActivityCompleted()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        companionViewModel.getCompanionList()
    }


}