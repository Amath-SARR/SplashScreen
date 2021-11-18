package com.example.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityDepotBinding
import com.example.splashscreen.databinding.ActivitySignInBinding

class Depot : AppCompatActivity() {
    lateinit var binding: ActivityDepotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_depot)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_depot)

        binding.retour.setOnClickListener {
            finish();
        }
    }
}