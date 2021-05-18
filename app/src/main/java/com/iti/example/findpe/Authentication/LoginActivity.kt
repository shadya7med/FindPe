package com.iti.example.findpe.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe.MainActivity
import com.iti.example.findpe.R
import com.iti.example.findpe.databinding.ActivityLoginBinding
import com.iti.example.findpe.home.HomeActivity
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth



        binding.createNewEmailTxtLogin.setOnClickListener {
            val openCreateAccount : Intent = Intent(this, SignUpActivity::class.java)
            startActivity(openCreateAccount)
        }
        binding.loginBtnLogin.setOnClickListener {
            binding.emailEdtTxtLogin.apply {
                if(!text.isValidEmail()) {
                    error = "Entered Email is not valid"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            binding.passwordEdtTxtLogin.apply {
                if(text.length < 8){
                    error = "Password is not valid"
                    requestFocus()
                    return@setOnClickListener
                }
            }
            auth.signInWithEmailAndPassword(binding.emailEdtTxtLogin.text.toString(), binding.passwordEdtTxtLogin.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val signInIntent = Intent(this, HomeActivity::class.java)
                        signInIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(signInIntent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }


        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            //there's a user navigate to Main and terminate login Act
            val openMain = Intent(this, HomeActivity::class.java)
            startActivity(openMain)
            finish()
        }
    }


}
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()