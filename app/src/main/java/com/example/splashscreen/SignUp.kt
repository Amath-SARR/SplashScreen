package com.example.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivitySignUpBinding

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        //Boutton vers Activité de SignIn
        binding.txtSignIn.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        //Verifixation validité du formulaire
        binding.btnSignUp.setOnClickListener {
            val mail = binding.login.text.toString().trim()
            val mdp = binding.password.text.toString().trim()
            val name = binding.fullName.text.toString().trim()

            if (name.isEmpty()) {
                binding.fullName.error = "Email est obligatoire"
                return@setOnClickListener
            }
            else if (mail.isEmpty()) {
                binding.login.error = "Email est obligatoire"
                return@setOnClickListener
            } else if (mdp.isEmpty()) {
                binding.password.error = "Password est obligatoire"
                return@setOnClickListener
            } else if (mdp.length < 8) {
                binding.password.error = "Le password doit contenir au moins 8 caractères de votre choix"
                return@setOnClickListener
            } else {
                Toast.makeText(this, "succes", Toast.LENGTH_SHORT).show()
            }
        }
    }
}