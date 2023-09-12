package com.example.chatapplication.patients

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.ProfileFragment
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.ismaeldivita.chipnavigation.ChipNavigationBar

@Suppress("DEPRECATION")
class PatientsListActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: ChipNavigationBar
    private val fragment = ListPatientsFragment()
    var database: FirebaseDatabase? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_list)

        database = FirebaseDatabase.getInstance()

        supportActionBar?.hide()
        loadFragment()
        bottomNavigation = findViewById(R.id.bottom_navigation_chip)
        bottomNavigation.setItemSelected(R.id.chat)
        bottomNavigation.setOnItemSelectedListener {
            when (it) {
                R.id.chat -> {
                    val listPatientsFragment = ListPatientsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, listPatientsFragment).commit()

                }

                R.id.profile -> {

                        val profileDoctorsFragment =  ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, profileDoctorsFragment).commit()

                }

                R.id.info -> {

                    val infoFragmentPatient = InfoFragmentPatient()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, infoFragmentPatient).commit()

                }
                else -> {

                }
            }
        }
    }


    private fun loadFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence")
            .child(currentId!!)
            .setValue("Online")
    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence")
            .child(currentId!!)
            .setValue("Offline")
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }
}


