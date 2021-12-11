package com.example.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityDepotBinding
import com.google.firebase.firestore.FirebaseFirestore

class Depot : AppCompatActivity() {
    val fStore = FirebaseFirestore.getInstance()
    lateinit var binding: ActivityDepotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_depot)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_depot)

        binding.retour.setOnClickListener {
            finish();
        }
        /*binding.deposer.setOnClickListener(){
            val uid: String = binding.IdCompte.toString().trim()
            val montant: String = binding.montant.toString().trim()
            //deposer(uid, montant)
        }*/
    }
    /*fun déposer(uid: String,montant: Double) {
        val df = fStore.collection("Users").document(uid)
        df.get().addOnSuccessListener {
            val solde = it["solde"]
            var som = 0
            som = (som + montant).roundToInt()
            return (solde + som).roundToInt

        }
    }*/
}




