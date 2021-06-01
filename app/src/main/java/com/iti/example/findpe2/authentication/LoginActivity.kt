package com.iti.example.findpe2.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iti.example.findpe2.utils.isValidEmail
import com.iti.example.findpe2.utils.setAllClickable
import com.iti.example.findpe2.databinding.ActivityLoginBinding
import com.iti.example.findpe2.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.finishBtn.setOnClickListener{
            finish()
        }
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
            setLoading()

            auth.signInWithEmailAndPassword(binding.emailEdtTxtLogin.text.toString(), binding.passwordEdtTxtLogin.text.toString())
                .addOnCompleteListener(this) { task ->
                    clearLoading()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        if(auth.currentUser!!.isEmailVerified) {
                            val signInIntent = Intent(this, HomeActivity::class.java)
                            signInIntent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(signInIntent)
                            finish()
                        }else{
                            auth!!.currentUser!!.sendEmailVerification()
                            Toast.makeText(baseContext, "Email is not verified, an email is sent for verification", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed due to ${task.exception?.localizedMessage}",
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
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
    private fun setLoading(){
        binding.progressBar.visibility = View.VISIBLE
        binding.viewGroup.setAllClickable(false)
    }
    private fun clearLoading(){
        clearData()
        binding.progressBar.visibility = View.GONE
        binding.viewGroup.setAllClickable(true)

    }

    private fun clearData() {
        with(binding){
            emailEdtTxtLogin.setText("")
            passwordEdtTxtLogin.setText("")
        }
    }

}
