package com.example.splashscreen

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

open class TransactionBancaire {
    val fStore = FirebaseFirestore.getInstance()
    fun deposer(uid: String, montant: String) {
        val df = fStore.collection("Users").document(uid)
        df.get().addOnSuccessListener {
            val solde = it["solde"].toString()
            Log.e("SoldeINIT", "$solde")
            val balance = somme(solde.toDouble(), montant)
            val new: MutableMap<String, Any> = HashMap()
            new.put("solde", balance.toString())
            df.update(new)
        }
    }
    fun somme(a: Double, b: String): Double {
        return a + b.toDouble()
    }
    fun retrait(uid: String, montant: String) {
        val df = fStore.collection("Users").document(uid)
        df.get().addOnSuccessListener {
            val solde = it["solde"].toString()
            if(solde.toDouble() >= montant.toDouble()) {
                val balance = soustraction(solde.toDouble(),  montant)
                val new: MutableMap<String, Any> = HashMap()
                new.put("solde", balance.toString())
                df.update(new)
                //finish()
            }
        }
    }
    fun soustraction(a: Double, b: String): Double {
        return a - b.toDouble()
    }
    fun virement(numS: String, numD: String, montant: String){
        Log.w("touche virement", "touche virement touché")
        retrait(numS, montant)
        deposer(numD, montant)
    }
}