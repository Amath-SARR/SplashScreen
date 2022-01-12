package com.example.splashscreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityVirementBinding
import com.google.firebase.firestore.FirebaseFirestore

class Virement: AppCompatActivity() {
    private val permissionRequest = 101
    val fStore = FirebaseFirestore.getInstance()
    lateinit var binding: ActivityVirementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_virement)

        binding.retour.setOnClickListener {
            finish();
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 101)

        binding.btnVirement.setOnClickListener{
            val numS = binding.numEmetteur.text.toString().trim()
            val numD = binding.numDestinataire.text.toString().trim()
            val montant: String = binding.montantVirer.text.toString().trim()
            val df = fStore.collection("Users").document(numS)
            df.get().addOnSuccessListener {
                val solde = it["solde"].toString()
                if (solde.toDouble() >= montant.toDouble()){
                    TransactionBancaire().virement(numS,numD,montant)
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    val myMsg = "Cher client votre transfert de $montant FCFA a été bien reussi votre solde actuel est ${solde.toDouble() - montant.toDouble()} FCFA. MyBank vous remerci de votre fidélité Merci votre fidélité"
                    val myNumber: String = it["tel"].toString()
                    sendSMS(myNumber, myMsg)
                }else if (solde.toDouble() == 0.0){ Toast.makeText(this, "Cet compte est vide ", Toast.LENGTH_LONG).show()}
                else {
                    Toast.makeText(this, "Error solde insuffisant! ", Toast.LENGTH_SHORT).show()
                }
            }
            val dff = fStore.collection("Users").document(numD)
            dff.get().addOnSuccessListener {
                val soldes = it["solde"].toString()
                if (soldes.toDouble() >= montant.toDouble()){
                    val myMsg = "Cher client vous venez de recevoir un montant de $montant votre solde actuel est ${soldes.toDouble() + montant.toDouble()} FCFA"
                    val myNumber = it["tel"].toString()
                    sendSMS(myNumber, myMsg)
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