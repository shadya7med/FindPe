package com.iti.example.findpe

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        val signUpTextView :TextView = findViewById(R.id.txtView_createOne_login)
        signUpTextView.setOnClickListener {
            val openCreateAccount : Intent = Intent(this,CreateAccountActivity::class.java)
            startActivity(openCreateAccount)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            //there's a user navigate to Main and terminate login Act
            val openMain = Intent(this,MainActivity::class.java)
            openMain.putExtra(CreateAccountActivity.USER_EMAIL,currentUser.email)
            startActivity(openMain)
            finish()
        }
    }


}