package com.example.chatapplication.doctors

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplication.R
import com.example.chatapplication.maps.MapsFragment
import com.example.chatapplication.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ismaeldivita.chipnavigation.ChipNavigationBar

@Suppress("DEPRECATION", "UNUSED_EXPRESSION")
class DoctorsListActivity : AppCompatActivity() {

    private lateinit var bottomNav: ChipNavigationBar
    private val fragment = ListDoctorsFragment()
    var database: FirebaseDatabase? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctors_list)

        database = FirebaseDatabase.getInstance()

        supportActionBar?.hide()
        loadFragment()
        bottomNav = findViewById(R.id.bottom_navigation_chip_doctors)
        bottomNav.setItemSelected(R.id.chat_doctors)
        bottomNav.setOnItemSelectedListener {
            when (it) {
                R.id.chat_doctors -> {
                    val listDoctorsFragment = ListDoctorsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_doctors, listDoctorsFragment).commit()

                }

                R.id.profile_doctors -> {
                    val profilePatientsFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_doctors, profilePatientsFragment).commit()

                }

                R.id.info_doctors-> {
                    val infoFragmentDoctors = InfoFragmentDoctors()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_doctors, infoFragmentDoctors).commit()

                }
                R.id.map -> {

                    val mapFragment = MapsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_doctors, mapFragment).commit()
                }

                else -> {


                }
            }
        }
    }

    private fun loadFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_doctors, fragment)
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