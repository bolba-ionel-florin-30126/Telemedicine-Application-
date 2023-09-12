package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.chatapplication.patients.SignUpPatients

@Suppress("DEPRECATION")
class ChooseWhatYouAreActivity : AppCompatActivity() {

    private lateinit var doctor: ImageView
    private lateinit var patient:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_what_you_are)

        doctor = findViewById(R.id.doctor)
        patient = findViewById(R.id.patient)

        supportActionBar?.hide()

        patient.setOnClickListener {
            val intent = Intent(this, SignUpPatients::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
        }
        doctor.setOnClickListener {
            val intent = Intent(this, InsertPinActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right)
    }
}