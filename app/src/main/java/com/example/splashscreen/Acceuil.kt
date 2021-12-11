package com.example.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityAcceuilBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class Acceuil : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    //var firestoreReference: DocumentReference? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityAcceuilBinding
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_acceuil)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_acceuil)
        /*binding.logout.setOnClickListener{
            finish()
        }*/
        val msg: String? = intent.getStringExtra("name")
        binding.name.text = msg

        val sms = intent.getStringExtra("solde")
        binding.solde.text = sms
        /*val uid = FirebaseAuth.getInstance().currentUser?.apply {
            Log.e("TAG", uid)
            //Add the product to favorites under the UID
        }*/

        // Bottom Bar_Navigation
        val nav = findViewById<BottomNavigationView>(R.id.navBar)

        nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    val intent = Intent(this, Acceuil::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.depot -> {
                    val intent = Intent(this, Depot::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.retrait -> {
                    val intent = Intent(this, retrait::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.virement -> {
                    val intent = Intent(this, Virement::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true

                }
                else -> false
            }
        }
    }
}

