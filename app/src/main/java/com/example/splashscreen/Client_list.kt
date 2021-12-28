package com.example.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splashscreen.databinding.ActivityClientListBinding
import com.example.splashscreen.databinding.ActivityEspaceAdminBinding
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Client_list : AppCompatActivity() {
    val fStore = FirebaseFirestore.getInstance()
    private lateinit var recylerView : RecyclerView
    private lateinit var userArrayList : ArrayList<Client>
    private lateinit var MyAdapter : MyAdapter

    private lateinit var newRecylerview : RecyclerView
    private lateinit var newArrayList : ArrayList<Client>
    private lateinit var tempArrayList : ArrayList<Client>

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityClientListBinding
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_client_list)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_client_list)

        recylerView = binding.recyclerView //findViewById(R.id.recyclerView)
        recylerView.layoutManager = LinearLayoutManager(this)
        recylerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        MyAdapter = MyAdapter(userArrayList)
        recylerView.adapter = MyAdapter
        EventChangeListener()

        newRecylerview = binding.recyclerView //findViewById(R.id.recyclerView)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)
        newArrayList = arrayListOf<Client>()
        tempArrayList = arrayListOf<Client>()
    }

    private fun EventChangeListener(){
        fStore.collection("Users").orderBy("fullName", Query.Direction.ASCENDING).
        addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Firebase error", error.message.toString())
            }
            for (dc: DocumentChange in value?.documentChanges!!) {
                if (dc.type == DocumentChange.Type.ADDED) {
                    userArrayList.add(dc.document.toObject(Client::class.java))
                }
            }
            MyAdapter.notifyDataSetChanged()
        }
    }
}