package com.iti.example.findpe2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.authentication.CreateAccountActivity

class MainActivity : AppCompatActivity() {
    private lateinit var signout_btn : Button
    private lateinit var userImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signout_btn = findViewById(R.id.signout_btn)
        userImage = findViewById(R.id.authedUserImage)

        signout_btn.setOnClickListener{
            Firebase.auth.signOut()
            finish()
        }
        var userEmail  = intent.getStringExtra(CreateAccountActivity.USER_EMAIL)
        val userEmailTextView : TextView = findViewById(R.id.txtView_userEmail_main)
        userEmailTextView.text = FirebaseAuth.getInstance().currentUser!!.displayName
        Glide
            .with(this)
            .load(FirebaseAuth.getInstance().currentUser!!.photoUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(userImage);


    }
}