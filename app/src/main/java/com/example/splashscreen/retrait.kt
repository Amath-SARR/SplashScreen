package com.example.splashscreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityDepotBinding
import com.example.splashscreen.databinding.ActivityRetraitBinding
import com.google.firebase.firestore.FirebaseFirestore

class retrait  : AppCompatActivity()  {
    lateinit var binding: ActivityRetraitBinding
    val fStore = FirebaseFirestore.getInstance()
    private val permissionRequest = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrait)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrait)
        binding.retour.setOnClickListener {
            finish();
        }

        binding.retrait.setOnClickListener(){
            Log.w("depot", "Toch depot clicker")
            val uid = binding.numeroCompte.text.toString().trim()
            val montant: String = binding.montantRetirer.text.toString().trim()
            val solde = TransactionBancaire().retrait(uid, montant)
            if (solde != null){

                /*Toast(this).apply {
                    duration = Toast.LENGTH_SHORT
                    setGravity(Gravity.CENTER, 0, 0)
                    view = layoutInflater.inflate(R.layout.custom_toast,idtoast)
                }.show()*/
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                sendMessage()
            }else {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
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
        val uid = binding.numeroCompte.text.toString().trim()
        val montant: String = binding.montantRetirer.text.toString().trim()
        val df = fStore.collection("Users").document(uid)

        df.get().addOnSuccessListener {

            val solde: String = it["solde"].toString()
            val myMsg: String = "Cher client votre retrait de $montant a été bien traité votre solde actuel est ${solde.toDouble() - montant.toDouble()} ! MyBank vous remerci de votre fidélité Merci votre fidélité"
            val myNumber: String = it["tel"].toString()
            if (myNumber == "" || myMsg == "") {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                if (TextUtils.isDigitsOnly(myNumber)) {
                    val smsManager: SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(myNumber, null, myMsg, null, null)
                    //Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
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