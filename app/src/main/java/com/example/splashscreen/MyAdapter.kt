package com.example.splashscreen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList


class MyAdapter(private val userList: ArrayList<Client>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    val fStore = FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_client,
            parent,false)
        return MyViewHolder(itemView)
        //return MyViewHolder(itemView, viewType)

    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        val user: Client= userList[position]
        holder.fullName.text = user.fullName
        holder.login.text = user.login
        holder.tel.text = user.tel

        //var deleteClent = findViewById<ImageView?>(com.example.splashscreen.R.id.delete)

        holder.delete.setOnClickListener {
            fStore.collection("users").document(getItemId(position).toString())
                .delete()
                .addOnCompleteListener {

                    //Toast.makeText(holder.fullName, "Success!", Toast.LENGTH_SHORT).show()
                    Log.e("success","OK")
                }.addOnFailureListener {
                    Log.e("success","Failed")
                    //Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    /* Delete the User from Firestore
    fun deleteUser(uid: Int){
        fStore.collection("Users").document(uid.toString()).delete()

    }*/

    public class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var fullName: TextView = itemView.findViewById(R.id.tvname)
        val login: TextView = itemView.findViewById(R.id.tvlogin)
        val tel: TextView = itemView.findViewById(R.id.tvtel)

        val delete = itemView.findViewById<ImageView>(R.id.delete)
    }

}