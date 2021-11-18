package com.example.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivitySignInBinding

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivitySignInBinding

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_in)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        //Boutton vers Activité de SignUp voirAcceuil
        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.acceuil.setOnClickListener {
            val intent = Intent(this, Acceuil::class.java)
            startActivity(intent)
        }

        //Verifixation validité du formulaire
        binding.btnLogin.setOnClickListener {
            val mail = binding.login.text.toString().trim()
            val mdp = binding.password.text.toString().trim()

            if (mail.isEmpty()) {
                binding.login.error = "Email est obligatoire"
                return@setOnClickListener
            } else if (mdp.isEmpty()) {
                binding.password.error = "Password est obligatoire"
                return@setOnClickListener
            } else if (mdp.length < 8) {
                binding.password.error = "Le password doit contenir 8 caractères de votre choix"
                return@setOnClickListener
            } else {
                Toast.makeText(this, "succes", Toast.LENGTH_SHORT).show()
            }
        }
    }
}






