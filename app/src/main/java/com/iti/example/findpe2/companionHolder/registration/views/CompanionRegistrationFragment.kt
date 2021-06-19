package com.iti.example.findpe2.companionHolder.registration.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.iti.example.findpe2.R
import com.iti.example.findpe2.companionHolder.registration.viewmodels.RegistrationViewModel
import com.iti.example.findpe2.databinding.FragmentCompanionRegisterationBinding
import com.iti.example.findpe2.pojos.RegistrationInfo
import com.iti.example.findpe2.utils.isValidEmail


class CompanionRegistrationFragment : Fragment() {

    private lateinit var binding: FragmentCompanionRegisterationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanionRegisterationBinding.inflate(layoutInflater, container, false)

        val viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        binding.registrationProceedBtn.setOnClickListener {
            //validate user data
            val registrationInfo: RegistrationInfo
            with(binding){
                registrationFirstNameText.apply {
                    if (text.length < 3) {
                        error = "First name is not valid"
                        requestFocus()
                        return@setOnClickListener
                    }
                }
                registrationLastNameText.apply {
                    if (text.length < 3) {
                        error = "Last name is not valid"
                        requestFocus()
                        return@setOnClickListener
                    }
                }
                registrationEmailText.apply {
                    if (!text.isValidEmail()){
                        error = "Email is not valid"
                        requestFocus()
                        return@setOnClickListener
                    }
                }
                registrationPhoneText.apply {
                    if (text.length < 11){
                        error = "phone is not valid"
                        requestFocus()
                        return@setOnClickListener
                    }
                }
                registrationCountryText.apply {
                    if (text.length < 3){
                        error = "Country is not valid"
                        requestFocus()
                        return@setOnClickListener
                    }
                }
                registrationCityText.apply {
                    if (text.length < 3){
                        error = "City is not valid"
                        requestFocus()
                        return@setOnClickListener
                    }
                }
                registrationInfo =
                    RegistrationInfo(
                        registrationFirstNameText.text.toString(),
                        registrationLastNameText.text.toString(),
                        registrationEmailText.text.toString(),
                        registrationPhoneText.text.toString(),
                        registrationCountryText.text.toString(),
                        registrationCityText.text.toString()
                    )
                findNavController().navigate(CompanionRegistrationFragmentDirections.actionCompanionRegistrationFragmentToCompanionLevelFragment(registrationInfo))
            }


        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}