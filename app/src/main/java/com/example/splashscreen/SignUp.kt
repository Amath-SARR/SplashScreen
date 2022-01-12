package com.example.splashscreen

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivitySignUpBinding
        val fAuth = FirebaseAuth.getInstance()
        val fStore = FirebaseFirestore.getInstance()

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        //Boutton vers Activité de SignIn
        binding.txtSignIn.setOnClickListener {
            finish()
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 101)
        //Verifixation validité du formulaire
        binding.btnSignUp.setOnClickListener {
            val mail = binding.login.text.toString().trim()
            val mdp = binding.password.text.toString().trim()
            val name = binding.fullName.text.toString().trim()
            val tel = binding.tel.text.toString().trim()
            val adress = binding.adress.text.toString().trim()
            val solde = "0.0"

            if (name.isEmpty()) {
                binding.fullName.error = "Email est obligatoire"
                return@setOnClickListener
            }else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                binding.login.error = "Saisir une adresse mail correct"
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
            }
             else {
                binding.progressBar2.setVisibility(View.VISIBLE)
                //Register the user in firebase
                fAuth.createUserWithEmailAndPassword(mail,mdp).addOnCompleteListener(OnCompleteListener<AuthResult>
                { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this@SignUp, "Bravo! register succesfully", Toast.LENGTH_SHORT).show()
                        val df: DocumentReference = fStore.collection("Users").document(user.getUid())
                        val new: MutableMap<String, Any> = HashMap()
                        new.put("fullName", name)
                        new.put("login", mail)
                        new.put("tel", tel)
                        new.put("adrese", adress)
                        new.put("solde", solde)
                        new.put("isUser",1)
                        new.put("uid",user.uid)
                        df.set(new)
                        finish()
                    }
                }).addOnFailureListener {
                    binding.progressBar2.setVisibility(View.INVISIBLE)
                    Toast.makeText(this@SignUp, "Cet email existe déjà", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    //Fonction pour envoyer un méssage à un client
    private fun sendSMS(phoneNumber: String, message: String) {
        /*val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)*/
        val smsManager = SmsManager.getDefault() as SmsManager
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }
}
