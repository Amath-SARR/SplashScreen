package com.example.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.*
import kotlin.collections.ArrayList
import android.R
import android.app.Activity
import android.content.Context
import android.media.CamcorderProfile.get
import android.view.View
import android.view.ViewConfiguration.get
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.splashscreen.databinding.ActivityEspaceAdminBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.nio.file.Files.delete


class EspaceAdmin : AppCompatActivity() {
    val fStore = FirebaseFirestore.getInstance()
    private lateinit var recylerView : RecyclerView
    private lateinit var userArrayList : ArrayList<Client>
    private lateinit var MyAdapter : MyAdapter
    private lateinit var db: FirebaseFirestore

    private lateinit var newRecylerview : RecyclerView
    private lateinit var newArrayList : ArrayList<Client>
    private lateinit var tempArrayList : ArrayList<Client>

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityEspaceAdminBinding

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_espace_admin)
        binding = DataBindingUtil.setContentView(this, com.example.splashscreen.R.layout.activity_espace_admin)

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


        //getUserdata() r
        //setSupportActionBar(actionbar)
        /*val nav: Toolbar = binding.toolbar2
        nav.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.search -> {
                    val intent = Intent(this, Depot::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                R.id.depot -> {
                    val intent = Intent(this, Depot::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                R.id.retrait -> {
                    val intent = Intent(this, retrait::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                R.id.virement -> {
                    val intent = Intent(this, Virement::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                R.id.seDeconnecter -> {
                    val intent = Intent(this, Virement::class.java)
                    startActivity(intent)
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
            }*/


    }

    //RecycleView
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

    // Delete the User from Firestore
    /*fun deleteUser(){
        //fStore.collection("Users").document(uid.toString()).delete()
        val eventsRef: CollectionReference = fStore.collection("users")
        val docIdQuery: Query = eventsRef.whereEqualTo("docId", "awa.sarr@univ-thies.sn")
        docIdQuery.get().addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                for (document in task.getResult()!!) {
                    document.getReference().delete()
                        .addOnSuccessListener(object : OnSuccessListener<Void?> {
                            override fun onSuccess(aVoid: Void?) {
                                Log.d("TAG", "Document successfully deleted!")
                            }
                        }).addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(e: Exception) {
                            Log.w("TAG", "Error deleting document", e)
                        }
                    })
                }
            } else {
                Log.d("TAG", "Error getting documents: ",
                    task.getException()
                ) //Don't ignore potential errors!
            }
        }
    }*/

    fun deleteUser(index: Int) {
        //fStore.collection("Users").document(uid.toString()).delete()
        fStore.collection("users").document(index.toString())
            .delete()
            .addOnCompleteListener {
                Toast.makeText(this@EspaceAdmin, "Success!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@EspaceAdmin, "Error!!", Toast.LENGTH_SHORT).show()
            }
    }

    //Search Un element
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s: String?): Boolean {
                searchData(s)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){

                    newArrayList.forEach {

                        if (it.fullName?.toLowerCase(Locale.getDefault()).contains(searchText)){


                            tempArrayList.add(it)

                        }

                    }

                    newRecylerview.adapter!!.notifyDataSetChanged()

                }else{

                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    newRecylerview.adapter!!.notifyDataSetChanged()

                }
                return false

            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun searchData( s : String){
        db.collection("Document").whereEqualTo("search", s.toLowerCase())
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot>(){
                task<QuerySnapshot> -> {
                    modelList.clear()
                    pd.demiss()
                    for (doc : DocumentSnapshot : task.getResult )

            }
            })
    }
    private fun getUserdata() {


        tempArrayList.addAll(newArrayList)


        val adapter = MyAdapter(tempArrayList)
        val swipegesture = object : SwipeGesture(this){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

                val from_pos = viewHolder.adapterPosition
                val to_pos = target.adapterPosition

                Collections.swap(newArrayList,from_pos,to_pos)
                adapter.notifyItemMoved(from_pos,to_pos)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when(direction){

                    ItemTouchHelper.LEFT ->{

                        adapter.deleteItem(viewHolder.adapterPosition)
                    }

                    ItemTouchHelper.RIGHT -> {

                        val archiveItem = newArrayList[viewHolder.adapterPosition]
                        adapter.deleteItem(viewHolder.adapterPosition)
                        adapter.addItem(newArrayList.size,archiveItem)

                    }

                }

            }

        }
        val touchHelper = ItemTouchHelper(swipegesture)
        touchHelper.attachToRecyclerView(newRecylerview)
        newRecylerview.adapter = adapter
        adapter.setOnItemClickListener(object : MyAdapter1.onItemClickListener{
            fun onItemClick(position: Int) {

                //  Toast.makeText(this@MainActivity,"You Clicked on item no. $position",Toast.LENGTH_SHORT).show()

                val intent = Intent(this@EspaceAdmin,NewsActivity::class.java)
                intent.putExtra("fullName",newArrayList[position].fullName)
                intent.putExtra("login",newArrayList[position].login)
                intent.putExtra("tel",newArrayList[position].tel)
                intent.putExtra("news",news[position])
                startActivity(intent)

            }


        })

    }*/
}
