package com.example.chatapplication.doctors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.chatapplication.LogIn
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth

class VerificationEmailActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_email)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        btnNext = findViewById(R.id.btnNextVerification)

        btnNext.setOnClickListener {

                val intent = Intent(this, LogIn::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)

            }

    }
}