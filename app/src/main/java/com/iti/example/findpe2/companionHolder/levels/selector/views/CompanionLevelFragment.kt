package com.iti.example.findpe2.companionHolder.levels.selector.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.FragmentCompanionLevelBinding


class CompanionLevelFragment : Fragment() {
    lateinit var binding: FragmentCompanionLevelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentCompanionLevelBinding.inflate(layoutInflater, container, false)
        val args by navArgs<CompanionLevelFragmentArgs>()
        binding.levelProGetStartedBtn.setOnClickListener {
            this.findNavController().navigate(CompanionLevelFragmentDirections.actionCompanionLevelFragmentToProfessionalRequirementsFragment(args.registerationInfo))
        }
        binding.levelAmatuerGetStartedBtn.setOnClickListener {
            this.findNavController().navigate(CompanionLevelFragmentDirections.actionCompanionLevelFragmentToAmateurRequirementsFragment(args.registerationInfo))
        }
        binding.levelCompanionNameText.text = args.registerationInfo.firstName

        return binding.root
    }


}