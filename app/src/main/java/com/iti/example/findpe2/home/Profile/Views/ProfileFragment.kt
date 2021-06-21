package com.iti.example.findpe2.home.profile.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Constants
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentProfileBinding
import com.iti.example.findpe2.home.profile.bio.views.EditBioActivity
import com.iti.example.findpe2.home.profile.viewModels.ProfileViewModel
import com.iti.example.findpe2.home.profile.viewModels.ProfileViewModelFactory
import com.iti.example.findpe2.jobrequest.views.JobRequestActivity
import java.io.IOException


class ProfileFragment : Fragment() {

    lateinit var profileViewModel: ProfileViewModel
    lateinit var resultActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        profileViewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(
                ProfileFragmentArgs.fromBundle(requireArguments()).isShownAsCompanion,
                ProfileFragmentArgs.fromBundle(requireArguments()).companion,
                ProfileFragmentArgs.fromBundle(requireArguments()).isUserAlsoCompanion
            )
        ).get(ProfileViewModel::class.java)
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
        binding.addPhotosFabProfile.setOnClickListener {
            val openPhotoPicker = Intent(Intent.ACTION_GET_CONTENT)
            openPhotoPicker.type = "image/*"
            startActivityForResult(
                Intent.createChooser(openPhotoPicker, "Select Picture"),
                Constants.SELECT_IMAGE
            )
        }
        profileViewModel.onSuccessUploadingImage.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "The selected image has been uploaded",
                        Snackbar.LENGTH_LONG
                    ).show()
                    profileViewModel.onDoneNotifyingUserOfUploading()
                } else {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        "The images was not uploaded successfully ",
                        Snackbar.LENGTH_LONG
                    ).show()
                    profileViewModel.onDoneNotifyingUserOfUploading()
                }
            }
        }

        resultActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    profileViewModel.updateBio(result.data?.getStringExtra(Keys.UPDATED_BIO_KEY))
                }

            }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                data.data
                            )
                        profileViewModel.uploadUserImage(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (profileViewModel.isShownAsCompanion)
            inflater.inflate(R.menu.companion_list_menu, menu)
        else {
            if (profileViewModel.isUserAlsoCompanion) {
                inflater.inflate(R.menu.profile_bio_menu, menu)
            } else {
                super.onCreateOptionsMenu(menu, inflater)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.send_request_menu_item -> {
                val openCompanionHolderIntent = Intent(activity, JobRequestActivity::class.java)
                openCompanionHolderIntent.putExtra(
                    Keys.COMPANION_ID_KEY,
                    profileViewModel.companionUser
                )
                startActivity(openCompanionHolderIntent)
                true
            }
            R.id.edit_bio_item_profile -> {
                val openEditBioCompanionIntent = Intent(activity, EditBioActivity::class.java)
                openEditBioCompanionIntent.putExtra(
                    Keys.CURRENT_USER_AS_COMPANION,
                    profileViewModel.companionUser
                )
                resultActivityLauncher.launch(openEditBioCompanionIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        //profileViewModel.updateBio()
    }

}

