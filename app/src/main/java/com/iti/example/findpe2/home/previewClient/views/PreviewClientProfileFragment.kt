package com.iti.example.findpe2.home.previewClient.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.iti.example.findpe2.databinding.FragmentPreviewClientProfileBinding
import com.iti.example.findpe2.home.previewClient.viewmodels.PreviewClientViewModel
import com.iti.example.findpe2.home.previewClient.viewmodels.PreviewClientViewModelFactory
import com.iti.example.findpe2.home.profile.views.UserGalleryAdapter


class PreviewClientProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentPreviewClientProfileBinding.inflate(inflater, container, false)
        val previewClientViewModel =
            ViewModelProvider(
                this,
                PreviewClientViewModelFactory(
                    PreviewClientProfileFragmentArgs.fromBundle(requireArguments()).clientID
                )
            ).get(PreviewClientViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.previewClientViewModel = previewClientViewModel

        val galleryGridLayoutManager = GridLayoutManager(activity,3)
        galleryGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int)  = when (position % 3) {
                0 -> 1
                else -> 2
            }
        }
        binding.userGalleryRvPreviewClient.layoutManager = galleryGridLayoutManager
        //same rv as the normal profile so we use same adapter
        binding.userGalleryRvPreviewClient.adapter = UserGalleryAdapter()





        return binding.root
    }


}