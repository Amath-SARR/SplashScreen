package com.example.splashscreen

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil.setContentView
import com.example.splashscreen.databinding.ActivityDepotBinding
import com.google.firebase.firestore.FirebaseFirestore
import android.view.Gravity

class Depot: AppCompatActivity() {
    val fStore = FirebaseFirestore.getInstance()
    lateinit var binding: ActivityDepotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_depot)
        binding = setContentView(this, R.layout.activity_depot)
        binding.retour.setOnClickListener {
            finish();
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 101)
        binding.deposer.setOnClickListener(){
            val uid = binding.numeroCompte.text.toString().trim()
            val montant = binding.montantDeposer.text.toString().trim()
            val df = fStore.collection("Users").document(uid)
            df.get().addOnSuccessListener {
                val soldes = it["solde"].toString()
                if (montant.toDouble() != 0.0){
                    TransactionBancaire().deposer(uid, montant)
                    val view = layoutInflater.inflate(com.example.splashscreen.R.layout.custom_toast,  null)
                    val toast = Toast(applicationContext)
                    toast.setGravity(Gravity.CENTER_VERTICAL, 250, -800)
                    toast.duration = Toast.LENGTH_LONG
                    toast.setView(view)
                    toast.show()
                    val myMsg = "Cher client votre dépot de $montant a été bien reussi votre solde actuel est ${soldes.toDouble() + montant.toDouble()} ! MyBank vous remercie de votre fidélité"
                    val myNumber: String = it["tel"].toString()
                    //sendSMS(myNumber, myMsg)
                }
                finish()
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






