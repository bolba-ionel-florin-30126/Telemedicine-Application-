package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatapplication.doctors.SignUpDoctors

@Suppress("DEPRECATION")
class InsertPinActivity : AppCompatActivity() {

    private lateinit var editPin: EditText
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_pin)

        editPin = findViewById(R.id.editPin)
        btnNext=findViewById(R.id.btnNext)

        supportActionBar?.hide()

        editPin.addTextChangedListener(loginTextWatcher)

        btnNext.setOnClickListener {

           if (editPin.text.toString() == "1234"){
               val intent = Intent(this, SignUpDoctors::class.java)
               startActivity(intent)
               overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left)
           }else{
               Toast.makeText(
                   this@InsertPinActivity,
                   "The password you entered is incorrect.",
                   Toast.LENGTH_LONG
               ).show()
           }

        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val editPin = editPin.text.toString().trim()

            btnNext.isEnabled = editPin.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_right)
    }
}