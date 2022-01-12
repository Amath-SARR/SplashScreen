package com.example.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.splashscreen.databinding.ActivityAcceuilBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class Acceuil: AppCompatActivity() {
    lateinit var storage: FirebaseStorage
    lateinit var imageUri: Uri
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityAcceuilBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        storage = FirebaseStorage.getInstance()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_acceuil)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_acceuil)
        setContentView(binding.root)
        /*binding.logout.setOnClickListener{
            finish()
        }*/
        val msg: String? = intent.getStringExtra("name")
        binding.name.text = msg

        val sms = intent.getStringExtra("solde")
        binding.solde.text = sms

        val num = intent.getStringExtra("uid")
        binding.uid.text = num

        binding.profile.setOnClickListener() {
            val intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*")
            startActivityForResult(intent, 33)
        }

        // Bottom Bar_Navigation
        val nav = findViewById<BottomNavigationView>(R.id.navBar)
        val nav1 = findViewById<MaterialToolbar>(R.id.toolbar2)
        nav1.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.seDeconnecter -> {
                    finish()
                    val intent = Intent(this, SignIn::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                R.id.conseil -> {
                    Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }

        nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    Toast.makeText(this, "Vous êtes déjà dans le home! ", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.depot -> {
                    Toast.makeText(this, "Desolé! cette option est dédié au emplyé! ", Toast.LENGTH_SHORT).show()

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.retrait -> {
                    Toast.makeText(this, "Desolé! cette option est dédié au emplyé! ", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.virement -> {
                    val intent = Intent(this, Virement::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.contact -> {
                    val intent = Intent(this, Contact::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data?.getData() != null){
            val profileUri: Uri = data.getData()!!
            binding.profile.setImageURI(profileUri)
            val ref: StorageReference? =
                FirebaseAuth.getInstance().getUid()?.let {
                    storage.getReference().child("profile")
                        .child(it)
                }
            ref?.putFile(profileUri)?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                fun onSuccess(tasksnapshot: UploadTask.TaskSnapshot){
                    Toast.makeText(this, "register succesfully", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}


