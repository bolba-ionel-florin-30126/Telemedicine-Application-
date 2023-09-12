package com.example.chatapplication.patients

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapplication.ChatActivity
import com.example.chatapplication.R
import de.hdodenhof.circleimageview.CircleImageView

class PatientAdapter(val context:Context, private val userList: ArrayList<Patient>):
    RecyclerView.Adapter<PatientAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textName.text =  currentUser.name
        holder.textEmail.text = currentUser.email

        Glide.with(context).load(currentUser.patientProfileImage).placeholder(R.drawable.ic_baseline_person).into(holder.imageProfilePatient)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userList.size

    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName: TextView = itemView.findViewById(R.id.text_name_patients)
        val imageProfilePatient: CircleImageView = itemView.findViewById(R.id.profile_patient_photo_chat)
        val textEmail:TextView = itemView.findViewById(R.id.patients_email)
        val lastMessages:TextView = itemView.findViewById(R.id.last_message)

    }
}