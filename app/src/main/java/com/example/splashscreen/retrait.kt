package com.example.splashscreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.content.Intent
import android.widget.Toast
import android.app.PendingIntent
import android.view.Gravity
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityRetraitBinding
import com.google.firebase.firestore.FirebaseFirestore

class retrait  : AppCompatActivity() {
    lateinit var binding: ActivityRetraitBinding
    val fStore = FirebaseFirestore.getInstance()
    private val permissionRequest = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrait)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrait)
        binding.retour.setOnClickListener {
            finish()
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 101)
        binding.retrait.setOnClickListener() {
            val uid = binding.numeroCompte.text.toString().trim()
            val montant = binding.montantRetirer.text.toString().trim()
            val df = fStore.collection("Users").document(uid)
            df.get().addOnSuccessListener {
                val soldes = it["solde"].toString()
                if (soldes >= montant && montant.toDouble() != 0.0){
                    TransactionBancaire().retrait(uid, montant)
                    val view = layoutInflater.inflate(com.example.splashscreen.R.layout.custom_toast,  null)
                    val toast = Toast(this@retrait)
                    toast.setGravity(Gravity.CENTER_VERTICAL, 250, -800)
                    toast.duration = Toast.LENGTH_LONG
                    toast.setView(view)
                    toast.show()
                    //Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    val myMsg = "Cher client votre retrait de $montant a été bien traité votre solde actuel est ${soldes.toDouble() - montant.toDouble()} ! MyBank vous remerci de votre fidélité Merci votre fidélité"
                    val myNumber: String = it["tel"].toString()
                    sendSMS(myNumber, myMsg)
                    finish()
                }else if (soldes.toDouble() == 0.0){ Toast.makeText(this, "Cet compte est vide ", Toast.LENGTH_LONG).show()}
                else {
                    Toast.makeText(this, "Error solde insuffisant! ", Toast.LENGTH_SHORT).show()
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