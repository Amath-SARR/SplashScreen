package com.example.splashscreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.util.Log
import android.view.Gravity.CENTER_VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.api.Context
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import kotlin.collections.ArrayList


open class MyAdapter(private var userList: ArrayList<Client>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    val fStore = FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_client,
            parent,false)
        return MyViewHolder(itemView)
        //return MyViewHolder(itemView, viewType)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val user: Client= userList[position]
        holder.fullName.text = user.fullName
        holder.login.text = user.login
        holder.tel.text = user.tel
        holder.uid.text = user.uid
        holder.solde.text = user.solde

        //delete user
        holder.delete.setOnClickListener {
            var builder = AlertDialog.Builder(holder.fullName.context)
            builder.setTitle("Attention").setMessage("Vous êtes sûre de cette opération qui supprime définitivement cet client")
                .setPositiveButton(
                    "OUI",
                    DialogInterface.OnClickListener {dialog, which ->
                        fStore.collection("Users").document(user.uid.toString())
                            .delete()
                            .addOnSuccessListener {
                                Toast.makeText(holder.fullName.context, "Delete successfuly ", Toast.LENGTH_LONG)
                                    .show()
                            }.addOnFailureListener {
                                Log.e("Error", "Il y'a erreur")
                            }
                })
                .setNeutralButton("NON", DialogInterface.OnClickListener { dialogInterface,_ -> dialogInterface.cancel() })
                .setNegativeButton("Annuler", null)
                var dialog = builder.create()
                dialog.setIcon(R.drawable.img_delete)
                dialog.show()
        }
        //update user
        holder.edit.setOnClickListener(View.OnClickListener {
            val dialogPlus = DialogPlus.newDialog(holder.fullName.getContext())
                .setContentHolder(ViewHolder(R.layout.update_popup))
                .setExpanded(true, 1400)
                .create()
            val v: View
            v = dialogPlus.getHolderView()
            val fullName = v.findViewById<EditText>(R.id.fullName)
            val login = v.findViewById<EditText>(R.id.login)
            val tel = v.findViewById<EditText>(R.id.tel)
            val update = v.findViewById<Button>(R.id.btnUpdate)
            val uid = v.findViewById<EditText>(R.id.numeroCompte)
            val solde = v.findViewById<EditText>(R.id.solde)

            fullName.setText(user.getFullName())
            login?.setText(user.getLogin())
            tel.setText(user.getTel())
            solde?.setText(user.getSolde())
            uid.setText(user.getUid())
            dialogPlus.show()
            update.setOnClickListener {
                Toast.makeText(holder.fullName.context, "Update successfuly ", Toast.LENGTH_LONG).show()
                Log.e("Upd", "Success")
                val edit: MutableMap<String, Any> = HashMap()
                edit.put("fullName", fullName.getText().toString())
                edit.put("login", login.getText().toString())
                edit.put("tel", tel.getText().toString())
                edit.put("solde", solde.getText().toString())
                val df: DocumentReference = fStore.collection("Users").document(user.uid.toString())
                df.update(edit)

            }
        })
    }
    override fun getItemCount(): Int {
        return userList.size
    }

    open class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var fullName: TextView = itemView.findViewById(R.id.tvname)
        val login: TextView = itemView.findViewById(R.id.tvlogin)
        val tel: TextView = itemView.findViewById(R.id.tvtel)
        val uid: TextView = itemView.findViewById(R.id.tvNumCompte)
        val solde: TextView = itemView.findViewById(R.id.tvsolde)
        val delete = itemView.findViewById<ImageView>(R.id.delete)
        val edit = itemView.findViewById<ImageView>(R.id.edit)
    }
}

