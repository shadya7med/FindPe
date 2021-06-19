package com.iti.example.findpe2.upload

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.iti.example.findpe2.R
import com.iti.example.findpe2.constants.Keys
import com.iti.example.findpe2.databinding.ActivityUploadBinding
import com.iti.example.findpe2.utils.setAllClickable
import java.io.ByteArrayOutputStream

const val RESULT_GET_IMAGE = 2021
const val TAG = "Upload Photo"

class UploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding
    private lateinit var auth: FirebaseAuth
    private var imageUri: Uri? = null
    private var documentReq = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        documentReq = intent.getStringExtra(Keys.DOCUMENT_REQ_KEY) ?: ""

        binding.uploadTitleText.text = documentReq.replace("_"," ")
        binding.chooseFileBtn.setOnClickListener {
            var pickImageIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickImageIntent, RESULT_GET_IMAGE)
        }
        binding.closeUploadBtn.setOnClickListener{
            finish()
        }
        binding.uploadBtn.setOnClickListener {
            binding.uploadedPhotoImage.apply {
                if (imageUri == null) {
                    Toast.makeText(baseContext, "Please choose an image", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }
            setLoading()
            val user = auth.currentUser
            //upload image to firebase storage
            var ref = FirebaseStorage.getInstance().reference.child("Companions Data")
                .child(user!!.uid).child(documentReq)
            val imageByteArray = downSizeImage(imageUri!!)
            val upload = ref.putBytes(imageByteArray)
            upload.addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener { task ->
                        val photoUri = task.result
                        val resIntent = Intent()
                        resIntent.putExtra(Keys.UPLOADED_DOC_KEY, photoUri.toString())
                        setResult(RESULT_OK, resIntent)
                        finish()
                    }
                }else{
                    clearLoading()
                    Toast.makeText(baseContext, "upload failed try again", Toast.LENGTH_SHORT)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_GET_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data;
            Glide
                .with(this)
                .load(imageUri)
                .placeholder(R.drawable.no_image_preview)
                .into(binding.uploadedPhotoImage);

        }
    }

    private fun setLoading() {
        binding.uploadPhotoProgress.visibility = View.VISIBLE
        binding.uploadViewGroup.setAllClickable(false)
    }
    private fun clearLoading() {
        binding.uploadPhotoProgress.visibility = View.GONE
        binding.uploadViewGroup.setAllClickable(true)
    }


    private fun downSizeImage(uri: Uri): ByteArray {
        val fullBitmap =
            MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        val scaleWidth: Int = fullBitmap.width / 4
        val scaleHeight: Int = fullBitmap.height / 4

        val scaledBitmap =
            Bitmap.createScaledBitmap(fullBitmap, scaleWidth, scaleHeight, true)

        // 2. Instantiate the downsized image content as a byte[]
        val baos = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray();
    }

}