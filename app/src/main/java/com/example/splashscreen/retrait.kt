package com.example.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityDepotBinding
import com.example.splashscreen.databinding.ActivityRetraitBinding

class retrait : AppCompatActivity() {
    lateinit var binding: ActivityRetraitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrait)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrait)

        binding.retour.setOnClickListener {
            finish();
        }
    }
}