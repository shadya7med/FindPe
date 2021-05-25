package com.iti.example.findpe2.Authentication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.iti.example.findpe2.R
import com.iti.example.findpe2.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binder:ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private var imageUri : Uri? = null
    private val TAG = "SIGNUP_ACTIVITY"
    private val RESULT_GET_IMAGE = 1050
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binder.root)

        auth = Firebase.auth

        binder.addImageBtnSignup.setOnClickListener{
            var pickImageIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickImageIntent,RESULT_GET_IMAGE)
        }
        binder.signinTxtViewSignup.setOnClickListener{
            finish()
        }
        binder.signupBtnSignup.setOnClickListener{
            //validate user data
            binder.emailEdtTextSignup.apply {
                if (!text.isValidEmail()){
                    error = "Entered Email is not valid"
                    requestFocus()
                    return@setOnClickListener
                }

            }
            binder.passwordEdtTextSignup.apply {
                if(text.length < 8 ){
                    error = "Password is not valid"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            //authenticate user data
            auth.createUserWithEmailAndPassword(binder.emailEdtTextSignup.text.toString(), binder.passwordEdtTextSignup.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            //upload image to firebase storage
                            var ref = FirebaseStorage.getInstance().reference.child("Profile Pics").child(user!!.uid)
                            val upload = ref.putFile(imageUri!!)
                            upload.addOnCompleteListener{
                                if(it.isSuccessful){
                                    ref.downloadUrl.addOnCompleteListener{
                                        val profileUpdates = userProfileChangeRequest {
                                            displayName = binder.fullNameEdtTextSignup.text.toString()
                                            photoUri = it.result
                                        }

                                        user!!.updateProfile(profileUpdates)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {

                                                }
                                            }

                                    }
                                }
                            }



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RESULT_GET_IMAGE && resultCode == Activity.RESULT_OK){
            imageUri = data?.data;
            Glide
                .with(this)
                .load(imageUri)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binder.userImageImgViewSignup);
        }
    }
}