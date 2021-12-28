package com.example.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.*
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityEspaceAdminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class EspaceAdmin : AppCompatActivity() {
    val fStore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityEspaceAdminBinding

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_espace_admin)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_espace_admin)

        binding.cardView1.setOnClickListener {
            val intent = Intent(this, retrait::class.java)
            startActivity(intent)
        }
        binding.cardView2.setOnClickListener {
            val intent = Intent(this, Depot::class.java)
            startActivity(intent)
        }
        binding.cardView3.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        binding.cardView4.setOnClickListener {
            val intent = Intent(this, Client_list::class.java)
            startActivity(intent)
        }

        binding.exit.setOnClickListener {
            finish()
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        val msg: String? = intent.getStringExtra("name")
        binding.name.text = msg

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
