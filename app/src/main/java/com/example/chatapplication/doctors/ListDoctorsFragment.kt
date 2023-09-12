package com.example.chatapplication.doctors

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_doctors_list.*

@Suppress("UNREACHABLE_CODE")
class ListDoctorsFragment : Fragment() {

    private lateinit var doctorList: ArrayList<Doctors>
    private lateinit var adapterDoctor: DoctorsAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var database: DatabaseReference
    private lateinit var doctorRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_doctors, container_doctors, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference


        doctorList = ArrayList()
        adapterDoctor = DoctorsAdapter(context!!, doctorList)
        doctorRecyclerView = view.findViewById(R.id.doctor_recycler_view)
        doctorRecyclerView.layoutManager = LinearLayoutManager(context)
        doctorRecyclerView.adapter = adapterDoctor


        mDbRef.child("doctors").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentDoctors = postSnapshot.getValue(Doctors::class.java)
                    if (mAuth.currentUser?.uid != currentDoctors?.uid) {
                        doctorList.add(currentDoctors!!)
                    }
                }
                adapterDoctor.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
