package com.example.chatapplication.doctors

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.ChatActivity
import com.example.chatapplication.R

class DoctorsAdapter(val context:Context, private val doctorList: ArrayList<Doctors>):
    RecyclerView.Adapter<DoctorsAdapter.DoctorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.doctors_layout,parent,false)
        return DoctorsViewHolder(view)

    }
    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {
        val currentDoctors = doctorList[position]

        holder.textName.text =  currentDoctors.name
        holder.doctorDepartment.text = currentDoctors.department

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name",currentDoctors.name)
            intent.putExtra("uid",currentDoctors.uid)

            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return doctorList.size

    }
    class DoctorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName: TextView = itemView.findViewById(R.id.text_name_doctors)
        val doctorDepartment: TextView = itemView.findViewById(R.id.doctors_department)

    }
}