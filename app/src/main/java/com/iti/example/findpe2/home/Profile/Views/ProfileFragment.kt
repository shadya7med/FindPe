package com.iti.example.findpe2.home.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.iti.example.findpe2.databinding.FragmentProfileBinding
import com.iti.example.findpe2.home.profile.viewModels.ProfileViewModel


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.profileViewModel = profileViewModel

        binding.userInfoRvProfile.adapter = UserInfoAdapter()

        val galleryGridLayoutManager = GridLayoutManager(activity, 3)
        galleryGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position % 3) {
                0 -> 1
                else -> 2
            }
        }
        binding.userGalleryRvProfile.layoutManager = galleryGridLayoutManager

        binding.userGalleryRvProfile.adapter = UserGalleryAdapter()

        return binding.root
    }


}