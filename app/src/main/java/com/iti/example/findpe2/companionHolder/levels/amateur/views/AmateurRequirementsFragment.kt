package com.iti.example.findpe2.companionHolder.levels.amateur.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.iti.example.findpe2.R
import com.iti.example.findpe2.companionHolder.levels.amateur.viewmodels.AmateurReqViewModel
import com.iti.example.findpe2.companionHolder.levels.amateur.viewmodels.AmateurReqViewModelFactory
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentAmatuerRequirementsBinding
import com.iti.example.findpe2.upload.UploadActivity


private const val PROFILE_PIC_REQ = 1001
private const val ID_PIC_REQ = 1002
class AmateurRequirementsFragment : Fragment() {

    lateinit var binding: FragmentAmatuerRequirementsBinding
    lateinit var viewModel: AmateurReqViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAmatuerRequirementsBinding.inflate(layoutInflater, container, false)

        val args by navArgs<AmateurRequirementsFragmentArgs>()

        val viewModelFactory = AmateurReqViewModelFactory(args.registerationInfo)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AmateurReqViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.amateurPerspnalPictureUploadBtn.setOnClickListener {
            val uploadIntent = Intent(context, UploadActivity::class.java)
            uploadIntent.putExtra(Keys.DOCUMENT_REQ_KEY, "personal_picture")
            startActivityForResult(uploadIntent,PROFILE_PIC_REQ)
        }
        binding.amatuerIdCardUploadBtn.setOnClickListener {
            val uploadIntent = Intent(context, UploadActivity::class.java)
            uploadIntent.putExtra(Keys.DOCUMENT_REQ_KEY, "id_card")
            startActivityForResult(uploadIntent,ID_PIC_REQ)
        }
        viewModel.toastVisibility.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, "Please upload unchecked Documents", Toast.LENGTH_SHORT)
                viewModel.toastAppearanceCompleted()
            }
        })
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PROFILE_PIC_REQ && resultCode == AppCompatActivity.RESULT_OK){
            val url = data!!.getStringExtra(Keys.UPLOADED_DOC_KEY)
            viewModel.setProfilePicUrl(url!!)
        }else if (requestCode == ID_PIC_REQ && resultCode == AppCompatActivity.RESULT_OK){
            val url = data!!.getStringExtra(Keys.UPLOADED_DOC_KEY)
            viewModel.setIdUrl(url!!)
        }

    }
}