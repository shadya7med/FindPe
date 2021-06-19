package com.iti.example.findpe2.companionHolder.levels.professional.views

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
import com.iti.example.findpe2.companionHolder.levels.professional.viewmodels.ProReqViewModel
import com.iti.example.findpe2.companionHolder.levels.professional.viewmodels.ProReqViewModelFactory
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.FragmentProfessionalRequirementsBinding
import com.iti.example.findpe2.upload.UploadActivity

private const val PROFILE_PIC_REQ = 1001
private const val ID_PIC_REQ = 1002
private const val CENSORSHIP_REQ = 1003
private const val CRIMINAL_REQ = 1004
class ProfessionalRequirementsFragment : Fragment() {

    lateinit var binding: FragmentProfessionalRequirementsBinding
    lateinit var viewModel: ProReqViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfessionalRequirementsBinding.inflate(layoutInflater, container, false)

        val args by navArgs<ProfessionalRequirementsFragmentArgs>()

        val viewModelFactory = ProReqViewModelFactory(args.registerationInfo)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProReqViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.proPerspnalPictureUploadBtn.setOnClickListener {
            val uploadIntent = Intent(context, UploadActivity::class.java)
            uploadIntent.putExtra(Keys.DOCUMENT_REQ_KEY, "personal_picture")
            startActivityForResult(uploadIntent,PROFILE_PIC_REQ)
        }
        binding.proIdCardUploadBtn.setOnClickListener {
            val uploadIntent = Intent(context, UploadActivity::class.java)
            uploadIntent.putExtra(Keys.DOCUMENT_REQ_KEY, "id_card")
            startActivityForResult(uploadIntent,ID_PIC_REQ)
        }
        binding.proCensorshipIdUploadBtn.setOnClickListener {
            val uploadIntent = Intent(context, UploadActivity::class.java)
            uploadIntent.putExtra(Keys.DOCUMENT_REQ_KEY, "censorship_id")
            startActivityForResult(uploadIntent,CENSORSHIP_REQ)
        }
        binding.proCriminalRecUploadBtn.setOnClickListener {
            val uploadIntent = Intent(context, UploadActivity::class.java)
            uploadIntent.putExtra(Keys.DOCUMENT_REQ_KEY, "criminal_record")
            startActivityForResult(uploadIntent,CRIMINAL_REQ)
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
        }else if (requestCode == CENSORSHIP_REQ && resultCode == AppCompatActivity.RESULT_OK){
            val url = data!!.getStringExtra(Keys.UPLOADED_DOC_KEY)
            viewModel.setCensorshipUrl(url!!)
        }else if (requestCode == CRIMINAL_REQ && resultCode == AppCompatActivity.RESULT_OK){
            val url = data!!.getStringExtra(Keys.UPLOADED_DOC_KEY)
            viewModel.setCriminalRecUrl(url!!)
        }

    }

}