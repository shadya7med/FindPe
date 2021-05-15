package com.iti.example.findpe.home.saved.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.iti.example.findpe.databinding.FragmentSavedBinding
import com.iti.example.findpe.home.filter.viewModels.FilterViewModel


class SavedFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var binder:FragmentSavedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binder = FragmentSavedBinding.inflate(inflater,container,false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        binder.filterBtnSavedHome.setOnClickListener {
            navController.navigate(SavedFragmentDirections.actionSavedFragmentHomeToFilterFragment())
        }

        // saved State handle is  a map for returning date between fragments
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<MutableMap<String,Any>>(
            FilterViewModel.FULL_MAP_KEY)?.observe(
            viewLifecycleOwner) { result ->
            // Do something with the result.
            Log.i("FiSav", "${result[FilterViewModel.TO_DATE_KEY] as Long}")
        }


    }




}