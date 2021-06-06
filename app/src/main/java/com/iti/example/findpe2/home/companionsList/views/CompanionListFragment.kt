package com.iti.example.findpe2.home.companionsList.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iti.example.findpe2.databinding.FragmentCompanionListBinding
import com.iti.example.findpe2.home.companionsList.viewModels.CompanionListViewModel


class CompanionListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentCompanionListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner =  viewLifecycleOwner

        val companionViewModel = ViewModelProvider(this).get(CompanionListViewModel::class.java)
        binding.companionListViewModel = companionViewModel

        binding.companionListRv.adapter = CompanionListAdapter(CompanionListAdapter.CompanionListClickListener {
            companionViewModel.onLikeClick(it)
        })



        return binding.root
    }


}