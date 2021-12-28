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

        binding.btnVirement.setOnClickListener{
            Log.w("depot", "Toch vire clicker")
            val numS = binding.numEmetteur.text.toString().trim()
            Log.w("depot", "$numS")
            val numD = binding.numDestinataire.text.toString().trim()
            Log.w("depot", "$numD")
            val montant: String = binding.montantVirer.text.toString().trim()
            val arg = TransactionBancaire().virement(numS,numD,montant)
            if (arg != null){
                Toast.makeText(this, "Cher client votre opération a été bien traité! Merci votre fidélité", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Cher client votre solde est insuffissant", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun sendMessage() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            myMessage()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
                permissionRequest)
        }
    }

    private fun myMessage() {
        val uid = binding.numEmetteur.text.toString().trim()
        val montant: String = binding.montantVirer.text.toString().trim()
        val df = fStore.collection("Users").document(uid)

        df.get().addOnSuccessListener {

            val solde: String = it["solde"].toString()
            val myMsg: String = "Cher client votre vireement de $montant a été bien traité votre solde actuel est $solde MyBank vous remerci de votre fidélité!"
            val myNumber: String = it["tel"].toString()
            if (myNumber == "" || myMsg == "") {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                if (TextUtils.isDigitsOnly(myNumber)) {
                    val smsManager: SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                    Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please the number is incorrect ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults:
    IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myMessage();
            } else {
                Toast.makeText(this, "You don't have required permission to send a message",
                    Toast.LENGTH_SHORT).show();
            }
        }
    }
}