package com.iti.example.findpe.Authentication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.iti.example.findpe.R

class SignUpActivity : AppCompatActivity() {
    private lateinit var signupBtn : Button
    private lateinit var addImageBtn : ImageView
    private lateinit var fullNameField : EditText
    private lateinit var emailField : EditText
    private lateinit var passwordField : EditText
    private lateinit var userImage : ImageView
    private lateinit var auth: FirebaseAuth
    private var imageUri : Uri? = null
    private val TAG = "SIGNUP_ACTIVITY"
    private val RESULT_GET_IMAGE = 1050
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        signupBtn = findViewById(R.id.btn_signup_signup)
        emailField = findViewById(R.id.edtText_email_signup)
        passwordField = findViewById(R.id.edtText_password_signup)
        fullNameField = findViewById(R.id.edtText_FullName_signup)
        userImage = findViewById(R.id.userImage)
        addImageBtn = findViewById(R.id.addImage_btn)

        addImageBtn.setOnClickListener{
            var pickImageIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickImageIntent,RESULT_GET_IMAGE)
        }
        signupBtn.setOnClickListener{
            auth.createUserWithEmailAndPassword(emailField.text.toString(), passwordField.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            //upload image to firebase storage
                            var ref = FirebaseStorage.getInstance().reference.child("Profile Pics").child(user.uid)
                            val upload = ref.putFile(imageUri!!)
                            Log.i(TAG, "onCreate: 1")
                            upload.addOnCompleteListener{
                                if(it.isSuccessful){
                                    ref.downloadUrl.addOnCompleteListener{
                                        Log.i(TAG, "onCreate: 2")
                                        val profileUpdates = userProfileChangeRequest {
                                            displayName = fullNameField.text.toString()
                                            Log.i(TAG, "onCreate: "+imageUri)
                                            photoUri = it.result
                                        }

                                        user!!.updateProfile(profileUpdates)
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    Log.d(TAG, "User profile updated.")
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
                .into(userImage);
        }
    }
}