package com.example.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.splashscreen.databinding.ActivityAcceuilBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class Acceuil : AppCompatActivity() {
    lateinit var binding: ActivityAcceuilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_acceuil)

        val nav = findViewById<BottomNavigationView>(R.id.navBar)
        nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {

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


    /*override fun onBackPressed() {
        Builder(this)
            .setTitle("Really Exit?")
            .setMessage("Are you sure you want to exit?")
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes, object : OnClickListener() {
                fun onClick(arg0: DialogInterface?, arg1: Int) {
                    super@WelcomeActivity.onBackPressed()
                }
            }).create().show()
    }*/
