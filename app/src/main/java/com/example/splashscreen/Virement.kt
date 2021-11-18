package com.example.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityVirementBinding

class Virement : AppCompatActivity() {
    lateinit var binding: ActivityVirementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_virement)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_virement)

        binding.retour.setOnClickListener {
            finish();
        }
    }


}