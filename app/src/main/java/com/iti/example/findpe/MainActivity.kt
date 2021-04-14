package com.iti.example.findpe

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var userEmail  = intent.getStringExtra(CreateAccountActivity.USER_EMAIL)
        val userEmailTextView : TextView = findViewById(R.id.txtView_userEmail_main)
        userEmailTextView.text = userEmail
        
    }
}