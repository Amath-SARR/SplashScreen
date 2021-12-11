package com.example.splashscreen

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignIn : AppCompatActivity() {
    val fStore = FirebaseFirestore.getInstance()
    val fAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivitySignInBinding
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_in)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        //Boutton vers Activité de SignUp
        binding.signUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.forgot.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val login = view.findViewById<EditText>(R.id.login)
            builder.setView(view)
            builder.setPositiveButton("Reset") { _, _, ->
                forgotPassword(login)
            }
            builder.setNegativeButton("Close") { _, _, ->
                builder.show()
            }
        }

        //Verification validité du formulaire
        binding.btnLogin.setOnClickListener {
            val mail = binding.login.text.toString().trim()
            val mdp = binding.password.text.toString().trim()

            if (mail.isEmpty()) {
                binding.login.error = "Email est obligatoire"
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                binding.login.error = "Saisir une adresse mail correct"
                return@setOnClickListener
            }
            else if (mdp.isEmpty()) {
                binding.password.error = "Password est obligatoire"
                return@setOnClickListener
            } else if (mdp.length < 8) {
                binding.password.error = "Le password doit contenir 8 caractères de votre choix"
                return@setOnClickListener
            } else {

                binding.progressBar.setVisibility(View.VISIBLE)
                fAuth.signInWithEmailAndPassword(mail,mdp).addOnSuccessListener {
                val user = it.user

                checkProfile(user!!.uid)

                //intent.putExtra("email_id", data)
                }.addOnFailureListener {
                    println(it.message)
                }


            }
         }

        //Reset a new password

       }

      fun checkProfile(uid: String) {
        val df = fStore.collection("Users").document(uid)
        df.get().addOnSuccessListener {
            if(it["isAdmin"]!=null){
                Toast.makeText(this@SignIn, "Success!", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@SignIn, "Success!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SignIn,EspaceAdmin::class.java)
                startActivity(intent)
                finish()
            }
            else if(it["isUser"]!=null){
                Toast.makeText(this@SignIn, "Success!", Toast.LENGTH_SHORT).show()
                //Log.e("user id: ", it["login"].toString())
                val intent = Intent(this@SignIn,Acceuil::class.java)
                intent.putExtra("name", it["fullName"].toString())
                intent.putExtra("solde", it["solde"].toString())
                intent.putExtra("email_id", it["login"].toString())
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this@SignIn,"Login or password incorrect", Toast.LENGTH_SHORT).show()
            }
        }
      }

    private fun forgotPassword(login: EditText){

        if (login.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(login.text.toString()).matches()) {
            return
        }
        fAuth.sendPasswordResetEmail(login.text.toString()).addOnCompleteListener(){

            task -> if (task.isSuccessful){
                Toast.makeText(this, "Un mail a été envoyé sur ce email veuillez le vérifié! ", Toast.LENGTH_SHORT).show()

            }
        }
    }
}



